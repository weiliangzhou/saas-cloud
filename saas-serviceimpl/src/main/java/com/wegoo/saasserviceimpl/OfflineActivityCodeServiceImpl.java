package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.exception.BusinessException;
import com.wegoo.model.po.OfflineActivityCode;
import com.wegoo.saasdao.mapper.OfflineActivityCodeMapper;
import com.wegoo.saasservice.OfflineActivityCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2710:56
 */
@Service
@Slf4j
public class OfflineActivityCodeServiceImpl implements OfflineActivityCodeService {
    @Autowired
    private OfflineActivityCodeMapper offlineActivityCodeMapper;

    @Override
    public OfflineActivityCode getOneByActivityCode(String activityCode) {
        return offlineActivityCodeMapper.getOneByActivityCode(activityCode);
    }

    @Override
    public void updatePassByActivityCode(String activityCode) {
        int count = offlineActivityCodeMapper.updatePassByActivityCode(activityCode);
        if (count != 1) {
            BSUtil.isTrue(false, "签到错误，请重试！");
        }
    }

    @Override
    public OfflineActivityCode getOneByUserIdAndOfflineActivityId(String userId, Integer offlineActivityId) {
        return offlineActivityCodeMapper.getOneByUserIdAndOfflineActivityId(userId, offlineActivityId);
    }

    @Override
    public Integer getBuyCountByUserIdAndThemeId(String userId, Integer themeId) {
        return offlineActivityCodeMapper.getBuyCountByUserIdAndThemeId(userId, themeId);
    }

    @Override
    public void insert(OfflineActivityCode offlineActivityCode) {
        int count = offlineActivityCodeMapper.insertSelective(offlineActivityCode);
        if (count == 0) {
            BSUtil.isTrue(false, "支付回调，生成code失败");
        }
    }

    @Override
    public Integer getAlreadyBuyCountByUserIdAndThemeId(String userId, Integer activityThemeId, String merchantId) {
        return offlineActivityCodeMapper.getAlreadyBuyCountByUserIdAndThemeId(userId, activityThemeId, merchantId);
    }

    @Override
    public OfflineActivityCode getOneByActivityCodeAndIsUsed(String activityCode, Integer isUsed) {
        return offlineActivityCodeMapper.getOneByActivityCodeAndIsUsed(activityCode, isUsed);
    }

    @Override
    public int getUnUsedCountByUserIdAndOfflineThemeIdAndUnUsed(String userId, Integer activityThemeId) {
        return offlineActivityCodeMapper.getUnUsedCountByUserIdAndOfflineThemeIdAndUnUsed(userId, activityThemeId);
    }

    @Override
    public Boolean updateOfflineActivityCodeActivityId(String activityCode, Integer activityId) {
        if (StringUtils.isBlank(activityCode) || null == activityId) {
            throw new BusinessException("参数错误");
        }
        offlineActivityCodeMapper.updateOfflineActivityCodeActivityId(activityCode, activityId);
        return Boolean.TRUE;
    }
}
