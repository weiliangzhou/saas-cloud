package com.wegoo.saasservice;

import com.wegoo.model.po.OfflineActivityCode;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivity
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2710:54
 */
public interface OfflineActivityCodeService {
    OfflineActivityCode getOneByActivityCode(String activityCode);

    void updatePassByActivityCode(String activityCode);

    OfflineActivityCode getOneByUserIdAndOfflineActivityId(String userId, Integer offlineActivityId);

    Integer getBuyCountByUserIdAndThemeId(String userId, Integer themeId);

    void insert(OfflineActivityCode offlineActivityCode);

    Integer getAlreadyBuyCountByUserIdAndThemeId(String userId, Integer activityThemeId, String merchantId);

    OfflineActivityCode getOneByActivityCodeAndIsUsed(String activityCode, Integer isUsed);

    int getUnUsedCountByUserIdAndOfflineThemeIdAndUnUsed(String userId, Integer activityThemeId);

    /**
     * 修改活动地点编号
     *
     * @param activityCode 要修改的活动吗
     * @param activityId   活动地点
     */
    Boolean updateOfflineActivityCodeActivityId(String activityCode, Integer activityId);
}
