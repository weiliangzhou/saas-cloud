package com.wegoo.saasservice;

import com.wegoo.model.po.OfflineActivityTheme;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityThemeService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2911:30
 */
public interface OfflineActivityThemeService {
    List<OfflineActivityTheme> getOfflineActivityThemeListByQueryType(String merchantId, String queryType);

    OfflineActivityTheme getOfflineActivityThemeDetailByThemeId(String merchantId, Integer themeId);

    void updateBuyCountById(Integer activityThemeId);
}
