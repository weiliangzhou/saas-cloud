package com.wegoo.saasservice;

import com.wegoo.model.po.ActivateInfo;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivity
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2710:54
 */
public interface OfflineActivityInfoService {
    ActivateInfo getOneByActivityCode(String activityCode);

    void updatePassByActivityCode(String activityCode);
}
