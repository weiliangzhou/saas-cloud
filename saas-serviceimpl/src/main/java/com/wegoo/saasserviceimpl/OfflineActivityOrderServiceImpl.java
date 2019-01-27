package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.BigDecimalUtil.BigDecimalUtil;
import com.wegoo.model.po.OfflineActivity;
import com.wegoo.model.po.OfflineActivityCode;
import com.wegoo.model.po.OfflineActivityOrder;
import com.wegoo.model.po.OfflineActivityTheme;
import com.wegoo.model.vo.OfflineActivityOrderVo;
import com.wegoo.saasdao.mapper.OfflineActivityOrderMapper;
import com.wegoo.saasservice.OfflineActivityCodeService;
import com.wegoo.saasservice.OfflineActivityOrderService;
import com.wegoo.saasservice.OfflineActivityService;
import com.wegoo.saasservice.OfflineActivityThemeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityOrderServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2811:08
 */
@Service
@Slf4j
public class OfflineActivityOrderServiceImpl implements OfflineActivityOrderService {

    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityOrderMapper offlineActivityOrderMapper;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;


    @Override
    public OfflineActivityOrderVo findOrderDetailByOrderNo(String orderNo) {
        OfflineActivityOrder offlineActivityOrder = offlineActivityOrderMapper.selectByPrimaryKey(orderNo);
        OfflineActivityOrderVo offlineActivityOrderVo = new OfflineActivityOrderVo();
        if (StringUtils.isNotBlank(offlineActivityOrder.getRealName())) {
            offlineActivityOrderVo.setRealName(offlineActivityOrder.getRealName());
        }
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(offlineActivityOrder.getMerchantId(), offlineActivityOrder.getActivityThemeId());
        offlineActivityOrderVo.setThemeName(offlineActivityTheme.getThemeName());
        if (null != offlineActivityOrder.getActivityId()) {
            OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(offlineActivityOrder.getActivityId());
            offlineActivityOrderVo.setActivityAddress(offlineActivity.getActivityAddress());
            offlineActivityOrderVo.setActivityStartTime(offlineActivity.getActivityStartTime());
            offlineActivityOrderVo.setActivityEndTime(offlineActivity.getActivityEndTime());
        }
        offlineActivityOrderVo.setActivityPrice(offlineActivityOrder.getActivityPrice());
        offlineActivityOrderVo.setActivityPriceDesc(BigDecimalUtil.div(100, offlineActivityOrder.getActivityPrice(), 2) + "");
        offlineActivityOrderVo.setAmount(1);
        offlineActivityOrderVo.setActivityCode(offlineActivityOrder.getActivityCode());
        return offlineActivityOrderVo;
    }

    @Override
    public OfflineActivityOrder findOrderByActivityCode(String activityCode) {
        return offlineActivityOrderMapper.findOrderByActivityCode(activityCode);
    }

    @Override
    public List<OfflineActivityOrderVo> findOrderByParams(OfflineActivityOrderVo offlineActivityOrder) {
        List<OfflineActivityOrder> offlineActivityOrderList = offlineActivityOrderMapper.findOrderByParams(offlineActivityOrder);
        Integer isUsed = offlineActivityOrder.getIsUsed();
        List<OfflineActivityOrderVo> offlineActivityOrderVoList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Integer> activityIdList = offlineActivityService.getChangeActivityIdList();
        if (null == offlineActivityOrderList) {
            return offlineActivityOrderVoList;
        }
        for (OfflineActivityOrder offlineActivityOrder1 : offlineActivityOrderList) {
            OfflineActivityOrderVo offlineActivityOrderVo = new OfflineActivityOrderVo();
            String merchantId = offlineActivityOrder1.getMerchantId();
            Integer themeId = offlineActivityOrder1.getActivityThemeId();
            if (StringUtils.isNotBlank(offlineActivityOrder1.getRealName())) {
                offlineActivityOrderVo.setRealName(offlineActivityOrder1.getRealName());
            }
            offlineActivityOrderVo.setPhone(offlineActivityOrder1.getPhone());
            if (null != offlineActivityOrder1.getStartTime()) {
                offlineActivityOrderVo.setStartTime(offlineActivityOrder1.getStartTime());
            }
            if (null != offlineActivityOrder1.getEndTime()) {
                offlineActivityOrderVo.setEndTime(offlineActivityOrder1.getEndTime());
            }
            offlineActivityOrderVo.setActivityThemeId(offlineActivityOrder1.getActivityThemeId());
            offlineActivityOrderVo.setReferrerName(offlineActivityOrder1.getReferrerName());
            offlineActivityOrderVo.setReferrerPhone(offlineActivityOrder1.getReferrerPhone());
            offlineActivityOrderVo.setIsRetraining(offlineActivityOrder1.getIsRetraining());
            OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
            offlineActivityOrderVo.setImgUrl(offlineActivityTheme.getImgUrl());
            offlineActivityOrderVo.setThemeName(offlineActivityTheme.getThemeName());
            if (null != offlineActivityOrder1.getActivityId()) {
                offlineActivityOrderVo.setDetaileFlag(1);
                OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(offlineActivityOrder1.getActivityId());
                offlineActivityOrderVo.setActivityAddress(offlineActivity.getActivityAddress());
                if (activityIdList.contains(offlineActivityOrder1.getActivityId())) {
                    if (offlineActivityOrder1.getIsRetraining().equals(0)) {
                        if (offlineActivityOrder1.getChangeTimes() != null && offlineActivityOrder1.getChangeTimes() < 3) {
                            if (offlineActivity.getLimitCount() > (offlineActivity.getBuyCount())) {
                                offlineActivityOrderVo.setFlag(1);
                            } else {
                                offlineActivityOrderVo.setFlag(0);
                            }
                        } else {
                            offlineActivityOrderVo.setFlag(0);
                        }
                    } else {
                        offlineActivityOrderVo.setFlag(0);
                    }

                } else {
                    offlineActivityOrderVo.setFlag(0);
                }
            } else {
                offlineActivityOrderVo.setDetaileFlag(0);
            }
            OfflineActivityCode offlineActivityCode;
            if (null == isUsed) {
                offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(offlineActivityOrder1.getActivityCode());
                if (offlineActivityCode != null) {
                    offlineActivityOrderVo.setIsUsed(offlineActivityCode.getIsUsed());
                    offlineActivityOrderVo.setCreateTimeDesc(simpleDateFormat.format(offlineActivityOrder1.getCreateTime()));
                    offlineActivityOrderVo.setActivityPrice(offlineActivityOrder1.getActivityPrice());
                    offlineActivityOrderVo.setActivityPriceDesc(BigDecimalUtil.div(100, offlineActivityOrder1.getActivityPrice(), 2) + "");
                    offlineActivityOrderVo.setOrderNo(offlineActivityOrder1.getOrderNo());
                    offlineActivityOrderVo.setActivityId(offlineActivityOrder1.getActivityId());
                    offlineActivityOrderVo.setAmount(1);
                    offlineActivityOrderVoList.add(offlineActivityOrderVo);
                }
            } else {
                offlineActivityCode = offlineActivityCodeService.getOneByActivityCodeAndIsUsed(offlineActivityOrder1.getActivityCode(), isUsed);
                if (offlineActivityCode != null) {
                    offlineActivityOrderVo.setIsUsed(offlineActivityCode.getIsUsed());
                    offlineActivityOrderVo.setCreateTimeDesc(simpleDateFormat.format(offlineActivityOrder1.getCreateTime()));
                    offlineActivityOrderVo.setActivityPrice(offlineActivityOrder1.getActivityPrice());
                    offlineActivityOrderVo.setActivityPriceDesc(BigDecimalUtil.div(100, offlineActivityOrder1.getActivityPrice(), 2) + "");
                    offlineActivityOrderVo.setOrderNo(offlineActivityOrder1.getOrderNo());
                    offlineActivityOrderVo.setActivityId(offlineActivityOrder1.getActivityId());
                    offlineActivityOrderVo.setAmount(1);
                    offlineActivityOrderVoList.add(offlineActivityOrderVo);
                }
            }

        }
        return offlineActivityOrderVoList;
    }

    @Override
    public OfflineActivityOrder findOrderByOrderNo(String out_trade_no) {
        return offlineActivityOrderMapper.selectByPrimaryKey(out_trade_no);
    }

    @Override
    public void updateStatusByOrderNo(String out_trade_no, String payment_no, String paymentTime) {
        int count = offlineActivityOrderMapper.updateStatusByOrderNo(out_trade_no, payment_no, paymentTime);
        if (count == 0) {
            BSUtil.isTrue(false, "更新订单状态失败");
        }
    }

    @Override
    public int changeOrder(int activityId, String orderNo, String remark, int changeTimes, Date startTime, Date endTime) {
        return offlineActivityOrderMapper.changeOrder(activityId, orderNo, remark, changeTimes, startTime, endTime);
    }

    @Override
    public OfflineActivityOrder selectOneByOrderNo(String orderNo) {
        return offlineActivityOrderMapper.selectOneByOrderNo(orderNo);
    }

    @Override
    public Integer getBindUserOrderCount(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Integer orderCount = offlineActivityOrderMapper.getBindUserOrderCount(userId, merchantId);
        return orderCount;
    }

    @Override
    public Integer getUserOrderCount(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Integer orderCount = offlineActivityOrderMapper.getUserOrderCount(userId, merchantId);
        return orderCount;
    }

    @Override
    public Boolean updateOfflineActivityOrderInfo(OfflineActivityOrder offlineActivityOrder) {
        if (null == offlineActivityOrder) {
            throw new BuilderException("参数错误");
        }
        if (StringUtils.isBlank(offlineActivityOrder.getOrderNo())) {
            throw new BuilderException("参数错误");
        }
        offlineActivityOrderMapper.updateByPrimaryKeySelective(offlineActivityOrder);
        return Boolean.TRUE;
    }

    @Override
    public void insertActivateUser(OfflineActivityOrder offlineActivityOrder) {
        offlineActivityOrderMapper.insertSelective(offlineActivityOrder);
    }

    @Override
    public List<String> findBuyUserIdByUserId(String userId) {
        return offlineActivityOrderMapper.findBuyUserIdByUserId(userId);
    }


}
