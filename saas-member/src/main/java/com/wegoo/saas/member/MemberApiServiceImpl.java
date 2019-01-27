package com.wegoo.saas.member;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.wegoo.api.member.MemberApiService;
import com.wegoo.baseservice.BaseService;
import com.wegoo.constants.Available;
import com.wegoo.constants.BaseResultConstants;
import com.wegoo.constants.MemberLevelConstants;
import com.wegoo.constants.msg.MsgCode;
import com.wegoo.constants.user.Channel;
import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.exception.BSUtil;
import com.wegoo.exception.BusinessException;
import com.wegoo.exception.WxCodeVerfiyException;
import com.wegoo.model.groups.Update;
import com.wegoo.model.po.*;
import com.wegoo.model.vo.ActivateUser;
import com.wegoo.model.vo.LoginParams;
import com.wegoo.model.vo.MyUser;
import com.wegoo.model.vo.user.UserInfoAllVo;
import com.wegoo.model.vo.wechat.JsApiSignatureVo;
import com.wegoo.model.vo.wechat.UserInfoVo;
import com.wegoo.saas.member.feginService.OfflineActivityFeginService;
import com.wegoo.saasservice.*;
import com.wegoo.saasservice.wechat.WechatService;
import com.wegoo.tokenManager.TokenManager;
import com.wegoo.tokenManager.TokenModel;
import com.wegoo.utils.IdCardVerification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;

/**
 * @author 二师兄超级帅
 * @Title: UserController
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1011:28
 */
@Slf4j
@RestController
@RequestMapping("/qudao-member/saas/member")
public class MemberApiServiceImpl extends BaseService implements MemberApiService {
    @Autowired
    private UserReferrerService userReferrerService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private UserOpenIdService userOpenIdService;
    @Autowired
    private UserCertificationService userCertificationService;
    @Autowired
    private MsgSenderService msgSenderService;
    @Autowired
    private UserService userService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private OfflineActivityFeginService offlineActivityFeginService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;


    @Value("${server.port}")
    private String serverPort;

    /**
     * 普通用户登录注册
     */
    @Override
    @PostMapping("/loginAndRegister")
    public String loginAndRegister(@RequestBody LoginParams loginParams) {
        try {
            // 普通用户
            loginParams.setMemberLevel(2);
            if (loginParams.getChannelId() != 1) {
                return setFailResult(BaseResultConstants.HTTP_CODE_500, "无效的渠道");
            }
            String token = userLoginService.login(loginParams);
            return setSuccessResult(token);
        } catch (BusinessException | WxCodeVerfiyException e) {
            if (e instanceof BusinessException) {
                log.error(e.getMessage(), e);
                return setFailResult(BaseResultConstants.HTTP_CODE_500, e.getMessage());
            }
            log.error(e.getMessage(), e);
            return setFailResult(BaseResultConstants.HTTP_WX_CODE_902, e.getMessage());
        } catch (Exception el) {
            log.error("用户登录出现异常错误", el);
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "用户登录出现异常错误");
        }
    }

    @Override
    @PostMapping("/login")
    public String login(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String msgCode = jsonObject.getString("msgCode");
        if (StringUtils.isBlank(msgCode)) {
            BSUtil.isTrue(false, "请输入短信验证码");
        }
        boolean success = msgSenderService.checkCode(phone, msgCode, MsgCode.REGISTER_LOGIN);
        if (!success) {
            BSUtil.isTrue(false, "验证码错误");
        }
        User user = userService.getUserByPhone(phone, Available.NORMAL);
        if (user == null) {
            return setFailResult("qzc", "用户不存在，请先注册");
        }
        //返回用户登录态
        TokenModel model = tokenManager.createToken(user.getUserId());
        String token = model.getSignToken();
        return setSuccessResult(token);
    }

    /**
     * 激活导入用户
     */
    @Override
    @PostMapping("/activateUser")
    public String activateUser(@RequestBody @Validated(Update.class) ActivateUser activateUser) {
        String merchantId = activateUser.getMerchantId();
        String wechatCode = activateUser.getWechatCode();
        String phone = activateUser.getPhone();
        String msgCode = activateUser.getMsgCode();
        boolean success = msgSenderService.checkCode(phone, msgCode, MsgCode.ACTIVATE_USER);
        if (!success) {
            BSUtil.isTrue(false, "验证码错误");
        }
        try {
            String token = userLoginService.activateUser(merchantId, wechatCode, phone, 1, 1);
            return setSuccessResult(token);
        } catch (BusinessException | WxCodeVerfiyException e) {
            if (e instanceof BusinessException) {
                log.error(e.getMessage(), e);
                return setFailResult(BaseResultConstants.HTTP_CODE_500, e.getMessage());
            }
            log.error(e.getMessage(), e);
            return setFailResult(BaseResultConstants.HTTP_WX_CODE_902, e.getMessage());
        } catch (Exception el) {
            log.error("用户登录出现异常错误", el);
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "用户登录出现异常错误");
        }

    }


    /**
     * 微信登录
     */
    @PostMapping("/wxLogin")
    public String wxLogin(@RequestBody JSONObject jsonObject) {
        String wechatCode = jsonObject.getString("wechatCode");
        String merchantId = jsonObject.getString("merchantId");
        Integer channelId = jsonObject.getInteger("channelId");

        TokenModel model = null;
        try {
            model = userLoginService.wxLogin(wechatCode, merchantId, Channel.getChannelById(channelId));
        } catch (WxCodeVerfiyException e) {
            return setFailResult(BaseResultConstants.HTTP_WX_CODE_902, e.getMessage());
        } catch (Exception e) {
            log.error("登录失败", e);
        }
        if (model == null) {
            return setFailResult("qzc", "用户不存在，请先注册");
        }
        String token = model.getSignToken();
        return setSuccessResult(token);
    }

    /**
     * 业务员用户 登录注册
     */
    @Override
    @PostMapping("/busLogin")
    public String busLogin(@RequestBody LoginParams loginParams) {
        try {
            // 业务员用户
            loginParams.setMemberLevel(MemberLevelConstants.YWY);
            String token = userLoginService.busLogin(loginParams);
            return setSuccessResult(token);
        } catch (BusinessException | WxCodeVerfiyException e) {
            if (e instanceof BusinessException) {
                log.error(e.getMessage(), e);
                return setFailResult(BaseResultConstants.HTTP_CODE_500, e.getMessage());
            }
            log.error(e.getMessage(), e);
            return setFailResult(BaseResultConstants.HTTP_WX_CODE_902, e.getMessage());
        } catch (Exception el) {
            log.error("用户登录出现异常错误", el);
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "用户登录出现异常错误");
        }
    }

    /**
     * 完善用户信息
     */
    @PostMapping("/auth/perfectUserInfo")
    public String perfectUserInfo(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String idCardNum = jsonObject.getString("idCardNum");
        String realName = jsonObject.getString("realName");
        String orderNo = jsonObject.getString("orderNo");
        String address = jsonObject.getString("address");
        String phone = jsonObject.getString("phone");
        Integer activityId = jsonObject.getInteger("activityId");
        String msgCode = jsonObject.getString("msgCode");
        String profession = jsonObject.getString("profession");
        String brand = jsonObject.getString("brand");
        Integer validateFlag = jsonObject.getInteger("validateFlag");

        //如validateFlag = 0 ，则验证手机号
        if (0 == validateFlag.intValue()) {
            if (StringUtils.isBlank(msgCode)) {
                BSUtil.isTrue(false, "请输入短信验证码");
            }
            boolean success = msgSenderService.checkCode(phone, msgCode, MsgCode.REGISTER_LOGIN);
            if (!success) {
                BSUtil.isTrue(false, "验证码错误");
            }
        }
        Boolean result = userService.updatePerfectUserInfo(userId, activityId, idCardNum, orderNo, realName, phone, address, profession, brand);
        return setSuccessResult(result);
    }

    /**
     * 绑定用户推荐人
     */
    @Override
    @PostMapping("/auth/bindUserReferrer")
    public String bindUserReferrer(@RequestBody JSONObject jsonObject) {
        try {
            String userId = CurrentUserInfoContext.getUserID();
            String userRef = jsonObject.getString("referrer");
            String merchantId = jsonObject.getString("merchantId");
            User user = userService.getUserByUserId(userId);
            if (null == user) {
                return setFailResult(BaseResultConstants.HTTP_CODE_500, "绑定未成功 , 请联系您的客户经理");
            }
            //通过userid去查询saas_user_referrer，如果为null就直接绑定
            // 如果不为null,有两种情况，isbuy为null或者为0，也直接绑定
            //                          isbuy为1，就不变更绑定
            UserReferrer userReferrerRecord = userReferrerService.getUserReferrerByUserIdAndMerchant(userId, merchantId);
            if (null == userReferrerRecord) {
                UserReferrer userReferrer = userReferrerService.bindUserRef(userId, userRef, merchantId);
                log.info("绑定业务员成功，用户userId：" + userReferrer.getUserId() + "，业务员userId：" + userReferrer.getReferrer());
                return setSuccessResult(userReferrer);
            } else {
                Integer isBuy = userReferrerRecord.getIsBuy();
                if (null == isBuy || 0 == isBuy.intValue()) {
                    UserReferrer userReferrer = userReferrerService.changeUserReferrer(userId, userRef, merchantId);
                    log.info("该用户还未购买，用户业务员更换成功，用户userId：" + userReferrer.getUserId() +
                            "，业务员userId：" + userReferrer.getReferrer() + "，原业务员userId:" + userReferrerRecord.getReferrer());
                    return setSuccessResult(userReferrer);
                } else {
                    log.info("该用户已购买，用户业务员不做更换，用户userId：" + userReferrerRecord.getUserId() +
                            "，业务员userId：" + userReferrerRecord.getReferrer());
                    return setSuccessResult(userReferrerRecord);
                }
            }
        } catch (Exception el) {
            log.error("绑定业务员失败", el);
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "绑定未成功 , 请联系您的客户经理");
        }
    }

    /**
     * 绑定微信号
     */
    @PostMapping("/auth/bindUserInfoWechatNo")
    public String bindUserInfoWechatNo(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String weChatNo = jsonObject.getString("weChatNo");
        Boolean result = userInfoService.updateWeChatByUserId(userId, weChatNo);
        return setSuccessResult(result);
    }

    /**
     * 获取用户分享图片
     */
    @PostMapping("/auth/getH5QrCode")
    public String getH5QrCode(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        Integer themeId = jsonObject.getInteger("themeId");
        String merchantId = jsonObject.getString("merchantId");
        String url = jsonObject.getString("url");
        JSONObject paramJson = new JSONObject();
        paramJson.put("themeId", themeId);
        paramJson.put("merchantId", merchantId);
        String offlineResult = offlineActivityFeginService.getOfflineActivityThemeDetailByThemeId(paramJson);
        JSONObject userInfoJson = JSONObject.parseObject(offlineResult);
        String reCode = userInfoJson.getString("reCode");
        String reMsg = userInfoJson.getString("reMsg");
        String data = userInfoJson.getString("data");
        if (!"200".equals(reCode)) {
            setFailResult(reCode, reMsg);
        }
        OfflineActivityTheme offlineActivityTheme = JSONObject.parseObject(data, OfflineActivityTheme.class);
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
        Integer memberLevel = userMember.getMemberLevel();
        String shareImg;
        if (memberLevel == 99) {
            shareImg = userService.getUserShareImage(url, userId, userId, offlineActivityTheme.getQrBgImg(), themeId);
            return setSuccessResult(shareImg);
        } else {
            UserReferrer userReferrer = userReferrerService.getUserReferrerByUserIdAndMerchant(userId, merchantId);
            UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
            if (null == userReferrer) {
                return setFailResult(BaseResultConstants.HTTP_CODE_500, "不存在上级推荐人!");
            } else {
                shareImg = userService.getUserShareImage(url, userInfo.getUserId(), userReferrer.getReferrer(), offlineActivityTheme.getQrBgImg(), themeId);
                return setSuccessResult(shareImg);
            }
        }

    }

    /**
     * 获取我的推广海报
     */
    @PostMapping("/auth/getMyH5QrCode")
    public String getMyH5QrCode(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        String url = jsonObject.getString("url");
        JSONObject paramJson = new JSONObject();
        paramJson.put("merchantId", merchantId);
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
        Integer memberLevel = userMember.getMemberLevel();
        String bgImg = "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20181107/c7e18827cf40430280d0db9ebf4c7743.png";
        String shareImg;
        if (memberLevel == MemberLevelConstants.YWY) {
            shareImg = userService.getMyShareImage(url, userId, userId, bgImg, 9999);
            return setSuccessResult(shareImg);
        } else {
            UserReferrer userReferrer = userReferrerService.getUserReferrerByUserIdAndMerchant(userId, merchantId);
            UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
            if (null == userReferrer) {
                return setFailResult(BaseResultConstants.HTTP_CODE_500, "不存在上级推荐人!");
            } else {
                shareImg = userService.getMyShareImage(url, userInfo.getUserId(), userReferrer.getReferrer(), bgImg, 9999);
                return setSuccessResult(shareImg);
            }
        }

    }


    /**
     * 获取微信js执行权限
     */
    @PostMapping("/getGzhJsApiToken")
    public String getGzhJsApiToken(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String url = jsonObject.getString("url");
        JsApiSignatureVo signatureVo = wechatService.getJsApiSignatureVo(merchantId, url);
        return setSuccessResult(signatureVo);
    }

    /**
     * 获取用户绑定的上级业务员信息
     */
    @PostMapping("/auth/getUserReferrerInfo")
    public String getUserReferrerInfo(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        UserInfoAllVo userInfoVo = userReferrerService.getUserReferrerInfo(userId, merchantId);
        return setSuccessResult(userInfoVo);
    }


    @Override
    @PostMapping("/sendMsgCode")
    public String sendRegisterCode(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        Integer busCode = jsonObject.getInteger("busCode");
        msgSenderService.sendCode(phone, MsgCode.getMsgCodeType(busCode));
        return setSuccessResult();
    }

    @Override
    @PostMapping("/auth/certification")
    public String certification(@RequestBody Certification certification) {
        String userId = CurrentUserInfoContext.getUserID();
        certification.setUserId(userId);
        String idCardNum = certification.getIdCardNum();
        try {
            String errMsg = IdCardVerification.IDCardValidate(idCardNum);
            if (!errMsg.contains("该身份证有效")) {
                return setFailResult(BaseResultConstants.HTTP_CODE_500, errMsg);
            }

        } catch (ParseException e) {
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "身份证格式错误！");
        }
        Integer count = userCertificationService.checkIdCardNum(idCardNum);
        if (count > 0) {
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "该身份证号已认证!");
        }
        userCertificationService.certification(certification);
        return setSuccessResult();


    }


    @Override
    @PostMapping("/auth/getCertificationInfoByUserId")
    public String getCertificationInfoByUserId(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        UserCertification userCertification = userCertificationService.getCertificationInfoByUserId(userId);
        return setSuccessResult(userCertification);
    }

    @Override
    @PostMapping("/getUserOpenIdByUserIdAndMerchantIdAndChannel")
    public String getUserOpenIdByUserIdAndMerchantIdAndChannel(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchant = jsonObject.getString("merchantId");
        Integer channelId = jsonObject.getInteger("channel");
        Channel channel = Channel.getChannelById(channelId);
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByUserIdAndMerchantIdAndChannel(userId, merchant,
                channel);
        return setSuccessResult(userOpenId);
    }

    @Override
    @PostMapping("/auth/getUserMemberByUserIdAndMerchantId")
    public String getUserMemberByUserIdAndMerchatId(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
        return setSuccessResult(userMember);
    }

    @Override
    @PostMapping("/auth/getUserInfoByUserId")
    public String getUserInfoByUserId() {
        String userId = CurrentUserInfoContext.getUserID();
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        return setSuccessResult(userInfo);
    }

    @Override
    @PostMapping("/auth/getAllUserInfo")
    public String getAllUserInfo(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        Integer channel = jsonObject.getInteger("channel");
        UserInfoAllVo userInfo = userInfoService.getAllUserInfo(userId, merchantId, Channel.getChannelById(channel));

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userId", userId);
        jsonParam.put("merchantId", merchantId);
        String jsonString = offlineActivityFeginService.getBindUserOrderCount(jsonParam);
        JSONObject jsonResult = JSONObject.parseObject(jsonString, JSONObject.class);
        String reCode = jsonResult.getString("reCode");
        String reMsg = jsonResult.getString("reMsg");
        String data = jsonResult.getString("data");
        log.info("获取用户邀请数:" + jsonResult);
        if (!"200".equals(reCode)) {
            setFailResult(reCode, reMsg);
        }
        Integer count = Integer.parseInt(data == null ? "0" : data);
        userInfo.setOrderCount(count);


        String userOderJson = offlineActivityFeginService.getUserOrderCount(jsonParam);
        JSONObject userOrderResult = JSONObject.parseObject(userOderJson, JSONObject.class);
        String userOrderReCode = userOrderResult.getString("reCode");
        String userOrderReMsg = userOrderResult.getString("reMsg");
        String userOrderData = userOrderResult.getString("data");
        log.info("获取用户订单数:" + userOrderResult);
        if (!"200".equals(userOrderReCode)) {
            setFailResult(userOrderReCode, userOrderReMsg);
        }
        Integer userOrderCount = Integer.parseInt(userOrderData == null ? "0" : userOrderData);
        userInfo.setUserOrderCount(userOrderCount);
        return setSuccessResult(userInfo);
    }

    @Override
    @PostMapping("/auth/getMyUserList")
    public String getMyUserList(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        //String userId = "d9d1a1ea623744619e738f81907da56d";
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");


        if (pageSize != null && pageNum != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }

        MyUser myUser = new MyUser();
        Integer dateType = jsonObject.getInteger("dateType");
        if (null != dateType) {
            myUser.setDateType(dateType);
        }
        String phone = jsonObject.getString("phone");
        if (null != phone) {
            myUser.setPhone(phone);
        }
        String realName = jsonObject.getString("realName");
        if (null != realName) {
            myUser.setRealName(realName);
        }
        //0全部  1已购
        Integer queryType = jsonObject.getInteger("queryType");
        if (queryType != null) {
            myUser.setQueryType(queryType);
        }
        myUser.setUserId(userId);
        List<UserInfo> userInfoList = userInfoService.getMyUserList(myUser);
        return setSuccessResult(userInfoList);

    }

    @Override
    @PostMapping("/auth/getReferrerInfoByUserId")
    public String getReferrerInfoByUserId(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String userId = CurrentUserInfoContext.getUserID();
        UserInfo userInfo = userInfoService.getReferrerInfoByUserId(userId, merchantId);
        return setSuccessResult(userInfo);
    }


    @Override
    @PostMapping("/auth/getGzhUserInfo")
    public String getGzhUserInfo(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        try {
            UserInfoVo infoVo = wechatService.getGzhUserInfo(merchantId, null);
            return setSuccessResult(infoVo);
        } catch (Exception e) {
            log.error("获取微信用户信息出错", e);
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "获取微信用户信息出错");
        }
    }

    @Override
    @PostMapping("/getUserMemberByUserIdAndMerchantId")
    public String getUserMemberByUserIdAndMerchantIdWithoutToken(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String merchantId = jsonObject.getString("merchantId");
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
        return setSuccessResult(userMember);
    }

    @Override
    @PostMapping("/getUserInfoByUserId")
    public String getUserInfoByUserIdWithoutToken(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        if (userId == null) {
            setFailResult("500", "缺少参数");
        }
        String merchantId = jsonObject.getString("merchantId");
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
        //fixme 存在异常测试数据  userId不存在
        Integer memberLevel = userMember.getMemberLevel();
        //业务员 则返回 registerMobile
        //否则 上级的手机
        if (MemberLevelConstants.YWY == memberLevel) {
            userInfo.setReferrerPhone(userInfo.getRegisterMobile());
        } else {
            UserInfoAllVo userReferrerInfo = userReferrerService.getUserReferrerInfo(userId, merchantId);
            userInfo.setReferrerPhone(userReferrerInfo.getRegisterMobile());
        }
        return setSuccessResult(userInfo);
    }

    /**
     * 获取已购课程列表
     */
    @Override
    @PostMapping("/auth/getOrderList")
    public String getOrderList(@RequestBody JSONObject jsonObject) {
        Order order = new Order();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String phone = jsonObject.getString("phone");
        String realName = jsonObject.getString("realName");
        Date activityStartTime = jsonObject.getDate("activityStartTime");
        Date activityEndTime = jsonObject.getDate("activityEndTime");
        Integer orderStatus = jsonObject.getInteger("orderStatus");
        String userId = CurrentUserInfoContext.getUserID();
        Integer type = jsonObject.getInteger("type");
        switch (type) {
            case 1:
                order.setReferrer(userId);
                break;
            case 2:
                order.setReferrer(userId);
                order.setUserId(jsonObject.getString("userId"));
                break;
            default:
                order.setUserId(userId);
                break;
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户Id不能为空");
        }
        order.setRealName(realName);
        order.setMerchantId(merchantId);
        order.setPhone(phone);
        order.setOrderStatus(orderStatus);
        order.setActivityStartTime(activityStartTime);
        if (null != activityEndTime) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(activityEndTime);
            calendar.add(Calendar.DATE, 1);
            activityEndTime = calendar.getTime();
            order.setActivityEndTime(activityEndTime);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderService.getOrderList(order);
        List<Product> productList = productService.getProductList(order.getMerchantId());
        Map productImageMap = new HashMap<>();
        Map productContentTextMap = new HashMap<>();
        for (Product product : productList) {
            if (product == null) {
                continue;
            }
            Long productId = product.getId();
            String imageUrl = product.getImageUrl();
            productImageMap.put(productId, imageUrl);
            String contentText = product.getContentText();
            productContentTextMap.put(productId, contentText);
        }
        for (Order orderTemp : orderList) {
            Long productId = orderTemp.getProductId();
            String imageUrl = productImageMap.get(productId) == null ? "" : productImageMap.get(productId).toString();
            orderTemp.setImageUrl(imageUrl);
            String contentText = productContentTextMap.get("contentText") == null ? "" : productContentTextMap.get("contentText").toString();
            orderTemp.setContentText(contentText);
        }
        return setSuccessResult(orderList);
    }


    @PostMapping("/auth/changeConsultInfo")
    public String changeConsultInfo(@RequestBody JSONObject jsonObject) {
        String userId = CurrentUserInfoContext.getUserID();
        String consultPhone = jsonObject.getString("consultPhone");
        String consultWechatNo = jsonObject.getString("consultWechatNo");
        //0展示注册手机号 1展示咨询手机号 2展示咨询微信号
        Integer consultShowType = jsonObject.getInteger("consultShowType");
        //更新saas_user_info 表
        userInfoService.updateConsultPhoneAndWechatNoAndShowTypeByUserId(consultPhone, consultWechatNo, consultShowType, userId);
        return setSuccessResult();
    }

    @PostMapping("/auth/getConsultInfo")
    public String getConsultInfo() {
        String userId = CurrentUserInfoContext.getUserID();
        //0展示注册手机号 1展示咨询手机号 2展示咨询微信号
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        return setSuccessResult(userInfo);
    }


}
