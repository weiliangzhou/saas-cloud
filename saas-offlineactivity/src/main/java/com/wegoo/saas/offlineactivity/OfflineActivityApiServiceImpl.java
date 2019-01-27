package com.wegoo.saas.offlineactivity;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.wegoo.api.offlineactivity.OfflineActivityApiService;
import com.wegoo.baseservice.BaseService;
import com.wegoo.constants.BaseResultConstants;
import com.wegoo.constants.user.Channel;
import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.exception.BSUtil;
import com.wegoo.exception.BusinessException;
import com.wegoo.model.groups.Buy;
import com.wegoo.model.po.*;
import com.wegoo.model.vo.*;
import com.wegoo.model.vo.wechat.UserInfoVo;
import com.wegoo.saas.offlineactivity.feginService.UserFeignService;
import com.wegoo.saasdao.mapper.OfflineActivityOrderMapper;
import com.wegoo.saasservice.*;
import com.wegoo.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: UserController
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1011:28
 */
@Slf4j
@RestController
@RequestMapping("/qudao-offlineactivity/saas/offlineActivity")
public class OfflineActivityApiServiceImpl extends BaseService implements OfflineActivityApiService {
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private OfflineActivityOperatorService offlineActivityOperatorService;
    @Autowired
    private OfflineActivityOrderMapper offlineActivityOrderMapper;
    @Autowired
    private OfflineActivityInfoService offlineActivityInfoService;

    @Autowired
    private UserFeignService userFeignService;
    @Autowired
    private UserInfoService userInfoService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/auth/buy")
    public String offlineActivityBuy(@RequestBody OfflineActivityBuy offlineActivityBuy) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = offlineActivityBuy.getMerchantId();
        Integer themeId = offlineActivityBuy.getThemeId();
        log.info("开始生成订单================================>userId::" + userId);
        JSONObject queryUserInfo = new JSONObject();
        queryUserInfo.put("merchantId", merchantId);
        queryUserInfo.put("channel", Channel.H5.ordinal());
        String userInfoStr = userFeignService.getAllUserInfo(queryUserInfo);
        //userInfoStr 可能为空
        JSONObject userInfoJson = JSONObject.parseObject(userInfoStr);
        String reCode = userInfoJson.getString("reCode");
        String reMsg = userInfoJson.getString("reMsg");
        String data = userInfoJson.getString("data");
        if (!"200".equals(reCode)) {
            return setFailResult(reCode, reMsg);
        }
        //存在data为null
        if (data == null) {
            throw new BusinessException("获取用户信息失败");
        }
        JSONObject userInfo = JSONObject.parseObject(data);
        String referrer = userInfo.getString("referrer");
        String registerMobile = userInfo.getString("registerMobile");
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (merchant == null) {
            throw new BusinessException("商户号不存在:" + merchantId);
        }
        //查询产品信息
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
        if (offlineActivityTheme == null) {
            throw new BusinessException("无效商品");
        }
        if (offlineActivityTheme.getBuyCount() >= offlineActivityTheme.getLimitCount()) {
            throw new BusinessException("商品已售完");
        }
        //生成订单(订单号使用 年月日时分秒+mch_no+userId（自增的Id）)
        //生成订单操作日志流水表
        SimpleDateFormat sdf_yMdHm = new SimpleDateFormat("yyyyMMddHHmm");
        //获取userId的自增Id
        String userLongId = userInfo.getString("id");
        Integer productId = offlineActivityTheme.getId();
        //根据购买规则 判断是否重复购买
        //没有核销该主题下面的活动，则报错  请核销该主题下面的活动
        int unUsedCount = offlineActivityCodeService.getUnUsedCountByUserIdAndOfflineThemeIdAndUnUsed(userId, themeId);
        if (unUsedCount > 0) {
            throw new BusinessException("该活动已成功报名,不能重复购买");
        }

        String orderNo = sdf_yMdHm.format(new Date()) + merchantId + userLongId + productId;
        OfflineActivityOrder offlineActivityOrder = new OfflineActivityOrder();
        offlineActivityOrder.setOrderNo(orderNo);
        offlineActivityOrder.setActivityThemeId(themeId);
        //活动兑换码生成规则
        String activityCode = UUIDUtil.getUUID32();
        offlineActivityOrder.setActivityCode(activityCode);
        offlineActivityOrder.setUserId(userId);
        offlineActivityOrder.setCreateTime(new Date());
        offlineActivityOrder.setAvailable(1);
        offlineActivityOrder.setMerchantId(merchantId);
        offlineActivityOrder.setOrderStatus(0);
        offlineActivityOrder.setPhone(registerMobile);
        //是否复训 0不是1是
        offlineActivityOrder.setIsRetraining(0);
        //现阶段没有返佣
        offlineActivityOrder.setIsMaid(0);
        offlineActivityOrder.setActualMoney(offlineActivityTheme.getPrice());
        offlineActivityOrder.setActivityPrice(offlineActivityTheme.getPrice());
        offlineActivityOrder.setReferrer(referrer);
        offlineActivityOrder.setChangeTimes(0);
        offlineActivityOrder.setReferrerName(userInfo.getString("referrerName"));
        offlineActivityOrder.setReferrerPhone(userInfo.getString("referrerPhone"));
        log.info("订单数据" + offlineActivityOrder);
        try {
            offlineActivityOrderMapper.insertSelective(offlineActivityOrder);
        } catch (Exception e) {
            BSUtil.isTrue(false, "操作频繁，请在一分钟后重试", e);
        }
        JSONObject queryJson = new JSONObject();
        queryJson.put("merchantId", merchantId);
        queryJson.put("userId", userId);
        queryJson.put("channel", Channel.H5);
        String gzhAppId = merchant.getGzAppId();
        String openId = userInfo.getString("openId");
        String wxPayKey = merchant.getWxPayKey();
        WxPayVo wxPayVo = wxPayService.pay("127.0.0.1", openId, orderNo, String.valueOf(offlineActivityTheme.getPrice()), gzhAppId, merchantId, wxPayKey);
        return setSuccessResult(wxPayVo);
    }


    @Override
    @PostMapping("/getOfflineActivityThemeList")
    public String getOfflineActivityThemeList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String queryType = jsonObject.getString("queryType");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        if (pageSize != null && pageNum != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<OfflineActivityTheme> offlineActivityThemeList = offlineActivityThemeService.getOfflineActivityThemeListByQueryType(merchantId, queryType);
        return setSuccessResult(offlineActivityThemeList);
    }

    @Override
    @PostMapping("/getOfflineActivityThemeDetailByThemeId")
    public String getOfflineActivityThemeDetailByThemeId(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer themeId = jsonObject.getInteger("themeId");
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
        return setSuccessResult(offlineActivityTheme);
    }

    @Override
    @PostMapping("/auth/getOfflineActivityListByThemeId")
    public String getOfflineActivityListByThemeId(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        Integer activityThemeId = jsonObject.getInteger("activityThemeId");
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, activityThemeId);
        List<OfflineActivity> offlineActivityList = offlineActivityService.getOfflineActivityListByThemeId(merchantId, activityThemeId, userId);
        OfflineActivityThemeVo offlineActivityThemeVo = new OfflineActivityThemeVo();
        offlineActivityThemeVo.setOfflineActivityList(offlineActivityList);
        if (offlineActivityTheme != null) {
            offlineActivityThemeVo.setRealNameShow(offlineActivityTheme.getRealNameShow());
            offlineActivityThemeVo.setPhoneShow(offlineActivityTheme.getPhoneShow());
            offlineActivityThemeVo.setPpShow(offlineActivityTheme.getPpShow());
            offlineActivityThemeVo.setZyShow(offlineActivityTheme.getZyShow());
            offlineActivityThemeVo.setIdCardNumShow(offlineActivityTheme.getIdCardNumShow());
            offlineActivityThemeVo.setAddressShow(offlineActivityTheme.getAddressShow());
        } else {
            offlineActivityThemeVo.setRealNameShow(0);
            offlineActivityThemeVo.setPhoneShow(0);
            offlineActivityThemeVo.setPpShow(0);
            offlineActivityThemeVo.setZyShow(0);
            offlineActivityThemeVo.setIdCardNumShow(0);
            offlineActivityThemeVo.setAddressShow(0);
        }

        return setSuccessResult(offlineActivityThemeVo);
    }


    @Override
    @PostMapping("/auth/getOfflineActivityChangeList")
    public String getOfflineActivityChangeList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer activityThemeId = jsonObject.getInteger("activityThemeId");
        Integer activityId = jsonObject.getInteger("activityId");
        List<OfflineActivity> offlineActivityChangeList = offlineActivityService.getOfflineActivityChangeList(merchantId, activityThemeId, activityId);
        return setSuccessResult(offlineActivityChangeList);
    }

    @Override
    @PostMapping("/auth/getActivityOrderDetail")
    public String getActivityOrderDetail(@RequestBody JSONObject jsonObject) {
        String orderNo = jsonObject.getString("orderNo");
        String merchantId = jsonObject.getString("merchantId");
        OfflineActivityOrderVo offlineActivityOrderVo = offlineActivityOrderService.findOrderDetailByOrderNo(orderNo);
        OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(offlineActivityOrderVo.getActivityCode());
        offlineActivityOrderVo.setQrCodeUrl(offlineActivityCode.getQrCodeUrl());
        if (StringUtils.isNotBlank(merchantId)) {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("merchantId", merchantId);
            String reusltJson = userFeignService.getGzhUserInfo(jsonParam);
            JSONObject jsonResult = JSONObject.parseObject(reusltJson, JSONObject.class);
            String reCode = jsonResult.getString("reCode");
            String reMsg = jsonResult.getString("reMsg");
            String data = jsonResult.getString("data");
            if (!"200".equals(reCode)) {
                offlineActivityOrderVo.setSubscibeWechat(0);
            } else {
                UserInfoVo infoVo = JSONObject.parseObject(data, UserInfoVo.class);
                if (null == infoVo) {
                    offlineActivityOrderVo.setSubscibeWechat(0);
                } else {
                    if (infoVo.getErrcode() == null) {
                        offlineActivityOrderVo.setSubscibeWechat(infoVo.getSubscribe());
                    } else if (!infoVo.getErrcode().equals(200)) {
                        offlineActivityOrderVo.setSubscibeWechat(0);
                    }
                }
            }
        }
        return setSuccessResult(offlineActivityOrderVo);
    }

    @Override
    @PostMapping("/offlineLogin")
    public String operatorSignIn(@RequestBody JSONObject jsonObject) {
        String operator = jsonObject.getString("operator");
        String password = jsonObject.getString("password");
        String merchantId = jsonObject.getString("merchantId");
        OfflineActivityOperator offlineActivityOperator = new OfflineActivityOperator();
        offlineActivityOperator.setOperator(operator);
        offlineActivityOperator.setPassword(password);
        offlineActivityOperator.setMerchantId(merchantId);
        offlineActivityOperator.setAvailable(1);
        OfflineActivityOperator offlineActivityOperator1 = offlineActivityOperatorService.selectByOperatorAndPassword(offlineActivityOperator);
        if (offlineActivityOperator1 == null) {
            BSUtil.isTrue(false, "操作员登陆失败");
        }
        return setSuccessResult();

    }

    @Override
    @PostMapping("/signIn")
    public String signIn(@Validated(Buy.class) @RequestBody SignInVo signInVo) {
        //获取操作员
        String operator = signInVo.getOperator();
        if (null == operator) {
            return setFailResult("800", "重新登录！");
        }
        //通过操作员匹配
        //ss_offline_activity_operator
        String merchantId = signInVo.getMerchantId();
        OfflineActivityOperator offlineActivityOperator = offlineActivityOperatorService.getOneByOperator(operator, merchantId);
        if (null == offlineActivityOperator) {
            return setFailResult("800", "非法操作！");
        }
        Integer themeId = offlineActivityOperator.getActivityThemeId();
        log.info("操作员主题" + themeId);
        //获取code
        String activityCode = signInVo.getActivityCode();
        //通过code 做一个比对
        //如果正确则更新ss_offline_activity_code
        OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(activityCode);
        if (offlineActivityCode == null) {
            ActivateInfo offlineActivateInfo = offlineActivityInfoService.getOneByActivityCode(activityCode);
            if (offlineActivateInfo == null) {
                BSUtil.isTrue(false, "非法code!");
            }
            if (offlineActivateInfo.getIsUsed() == 1) {
                BSUtil.isTrue(false, "签到码已被使用！");
            }
            Integer activityId = offlineActivateInfo.getActivityId();
            OfflineActivity offlineActivityCheckTime = offlineActivityService.getOneByActivityIdAndCheckTime(activityId);
            if (offlineActivityCheckTime == null) {
                BSUtil.isTrue(false, "活动未开始或者已过期！");
            }
            offlineActivityInfoService.updatePassByActivityCode(activityCode);
        } else {
            Integer themeId_code = offlineActivityCode.getActivityThemeId();
            log.info("操作员主题" + themeId_code);
            if (!themeId.equals(themeId_code)) {
                return setFailResult("800", "无权核销该课程 , 请切换正确账户登入！");
            }
            if (offlineActivityCode.getIsUsed() == 1) {
                BSUtil.isTrue(false, "签到码已被使用！");
            }
            Integer activityId = offlineActivityCode.getActivityId();
            //需要判断当前下线活动  是否在活动期间
            //如果在的话 则更新
            //如果不在  则报错 签到活动已经到期
            OfflineActivity offlineActivityCheckTime = offlineActivityService.getOneByActivityIdAndCheckTime(activityId);
            if (offlineActivityCheckTime == null) {
                BSUtil.isTrue(false, "活动未开始或者已过期！");
            }
            offlineActivityCodeService.updatePassByActivityCode(activityCode);

        }
        return setSuccessResult("200", "确认到场");
    }

    @Override
    @PostMapping("/getActivityCodeDetail")
    public String getActivityCodeDetail(@RequestBody JSONObject jsonObject) {
        String activityCode = jsonObject.getString("activityCode");
        OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(activityCode);

        if (offlineActivityCode == null) {
            ActivateInfo offlineActivateInfo = offlineActivityInfoService.getOneByActivityCode(activityCode);
            if (offlineActivateInfo == null) {
                BSUtil.isTrue(false, "非法code!");
            }
            Integer activityId = offlineActivateInfo.getActivityId();
            OfflineActivity offlineActivityCheckTime = offlineActivityService.getOneByActivityIdAndCheckTime(activityId);
            if (offlineActivityCheckTime == null) {
                BSUtil.isTrue(false, "活动未开始或者已过期！");
            } else {
                ActivityCodeDetail activityCodeDetail = new ActivityCodeDetail();
                activityCodeDetail.setRealName(offlineActivateInfo.getRealName());
                activityCodeDetail.setPhone(offlineActivateInfo.getPhone());
                activityCodeDetail.setIdCardNum(offlineActivateInfo.getIdCardNum());
                activityCodeDetail.setStatus("线下");
                activityCodeDetail.setActivityAddress(offlineActivityCheckTime.getActivityAddress());
                activityCodeDetail.setActivityStartTime(offlineActivityCheckTime.getActivityStartTime());
                activityCodeDetail.setActivityEndTime(offlineActivityCheckTime.getActivityEndTime());
                activityCodeDetail.setLogoUrl("http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180930/be406a40059343eb8e4952300e063149.jpg");
                return setSuccessResult(activityCodeDetail);
            }
        } else {
            Integer activityId = offlineActivityCode.getActivityId();
            OfflineActivity offlineActivityCheckTime = offlineActivityService.getOneByActivityIdAndCheckTime(activityId);
            if (offlineActivityCheckTime == null) {
                BSUtil.isTrue(false, "活动未开始或者已过期！");
            } else {
                //查询订单号
                OfflineActivityOrder offlineActivityOrder = offlineActivityOrderService.findOrderByActivityCode(activityCode);
                String userId = offlineActivityOrder.getUserId();
                ActivityCodeDetail activityCodeDetail = new ActivityCodeDetail();
                activityCodeDetail.setRealName(offlineActivityOrder.getRealName());
                activityCodeDetail.setPhone(offlineActivityOrder.getPhone());
                activityCodeDetail.setIdCardNum(offlineActivityOrder.getIdCardNum());
                //第一次购买则显示初次
                //否则就是复训
                //通过userid 查看当前用户 是否已经购买过该主题
                Integer themeId = offlineActivityCheckTime.getActivityThemeId();
                Integer userBuyCount = offlineActivityCodeService.getBuyCountByUserIdAndThemeId(userId, themeId);
                if (userBuyCount == 1) {
                    activityCodeDetail.setStatus("初次");
                } else {
                    activityCodeDetail.setStatus("复训");
                }
                activityCodeDetail.setActivityAddress(offlineActivityCheckTime.getActivityAddress());
                activityCodeDetail.setActivityStartTime(offlineActivityCheckTime.getActivityStartTime());
                activityCodeDetail.setActivityEndTime(offlineActivityCheckTime.getActivityEndTime());
                UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
                String logoUrl = userInfo.getLogoUrl();
                activityCodeDetail.setLogoUrl(logoUrl == null ? "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180930/be406a40059343eb8e4952300e063149.jpg" : logoUrl);
                return setSuccessResult(activityCodeDetail);
            }
        }
        return null;
    }

    @Override
    @PostMapping("/auth/getActivityOrderList")
    public String getActivityOrderList(@RequestBody JSONObject jsonObject) {
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer type = jsonObject.getInteger("type");
        String userId = CurrentUserInfoContext.getUserID();
        if (pageSize != null && pageNum != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        OfflineActivityOrderVo offlineActivityOrder = new OfflineActivityOrderVo();
        if (StringUtils.isNotBlank(jsonObject.getString("realName"))) {
            offlineActivityOrder.setRealName(jsonObject.getString("realName"));
        }
        if (StringUtils.isNotBlank(jsonObject.getString("phone"))) {
            offlineActivityOrder.setPhone(jsonObject.getString("phone"));
        }
        if (StringUtils.isNotBlank(jsonObject.getString("activityStartTime"))) {
            offlineActivityOrder.setActivityStartTime(jsonObject.getDate("activityStartTime"));
        }
        if (StringUtils.isNotBlank(jsonObject.getString("activityEndTime"))) {
            Date activityEndTime = jsonObject.getDate("activityEndTime");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(activityEndTime);
            //把日期往后增加一天,整数  往后推,负数往前移动
            calendar.add(Calendar.DATE, 1);
            activityEndTime = calendar.getTime();
            offlineActivityOrder.setActivityEndTime(activityEndTime);
        }
        if (type.equals(1)) {
            offlineActivityOrder.setReferrer(userId);
        } else if (type.equals(2)) {
            offlineActivityOrder.setReferrer(userId);
            offlineActivityOrder.setUserId(jsonObject.getString("referrer"));
        } else {
            offlineActivityOrder.setUserId(userId);
        }

        offlineActivityOrder.setMerchantId(jsonObject.getString("merchantId"));
        if (jsonObject.getInteger("isUsed") != null) {
            offlineActivityOrder.setIsUsed(jsonObject.getInteger("isUsed"));
        }
        List<OfflineActivityOrderVo> offlineActivityOrderVoList = offlineActivityOrderService.findOrderByParams(offlineActivityOrder);
        return setSuccessResult(offlineActivityOrderVoList);
    }

    @Override
    @PostMapping("/auth/change")
    public String change(@RequestBody JSONObject jsonObject) {
        Integer activityId = jsonObject.getInteger("activityId");
        String orderNo = jsonObject.getString("orderNo");
        OfflineActivityOrder order = offlineActivityOrderService.selectOneByOrderNo(orderNo);
        if (order != null) {
            if (order.getIsRetraining().equals(0)) {
                if (order.getChangeTimes() != null && order.getChangeTimes() < 3) {
                    int changeTimes = order.getChangeTimes() + 1;
                    int flag = offlineActivityService.getStartTime(order.getActivityId());
                    if (flag > 0) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(activityId);
                        Integer byeCount = offlineActivity.getBuyCount();
                        if (!offlineActivity.getLimitCount().equals(byeCount)) {
                            Integer oldActivityId = order.getActivityId();
                            String remark = order.getRemark();
                            if (StringUtils.isBlank(remark)) {
                                String createTime = sdf.format(order.getCreateTime());
                                remark = oldActivityId.toString() + "," + activityId.toString() + "," + createTime;
                            } else {
                                String modifyTime = sdf.format(order.getModifyTime());
                                remark = remark + "/" + oldActivityId.toString() + "," + activityId.toString() + "," + modifyTime;
                            }
                            offlineActivityService.updateBuyCountById(activityId);
                            offlineActivityService.updateOldBuyCountById(oldActivityId);
                            int result = offlineActivityOrderService.changeOrder(activityId, orderNo, remark, changeTimes, offlineActivity.getActivityStartTime(), offlineActivity.getActivityEndTime());
                            if (result == 1) {
                                return setSuccessResult();
                            } else {
                                return setFailResult(BaseResultConstants.HTTP_CODE_500, "改签失败！");
                            }
                        } else {
                            return setFailResult(BaseResultConstants.HTTP_CODE_500, "购买人数已满，无法改签！");
                        }

                    } else {
                        return setFailResult(BaseResultConstants.HTTP_CODE_500, "距离开始时间不满七天，无法改签！");
                    }

                } else {
                    return setFailResult(BaseResultConstants.HTTP_CODE_500, "改签次数已达三次，不能再次改签！");
                }
            } else {
                return setFailResult(BaseResultConstants.HTTP_CODE_500, "复训不能再次改签！");
            }
        } else {
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "未查询到订单！");
        }

    }

    @Override
    @PostMapping("/auth/getBindUserOrderCount")
    public String getBindUserOrderCount(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String merchantId = jsonObject.getString("merchantId");
        Integer orderCount = offlineActivityOrderService.getBindUserOrderCount(userId, merchantId);
        return setSuccessResult(orderCount);
    }

    /**
     * 获取用户下单数
     */
    @Override
    @PostMapping("/auth/getUserOrderCount")
    public String getUserOrderCount(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        Integer orderCount = offlineActivityOrderService.getUserOrderCount(userId, merchantId);
        return setSuccessResult(orderCount);
    }

}
