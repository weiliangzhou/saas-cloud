package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.OfflineActivityTheme;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OfflineActivityThemeMapper {
    OfflineActivityTheme selectByPrimaryKey(Integer id);

    List<OfflineActivityTheme> getOfflineActivityThemeListByQueryType(@Param("merchantId") String merchantId, @Param("queryType") String queryType);

    OfflineActivityTheme getOfflineActivityThemeDetailByThemeId(@Param("merchantId") String merchantId, @Param("themeId") Integer themeId);

    @Update("update saas_offline_activity_theme set buy_count=buy_count+1 where  id =#{activityThemeId}")
    int updateBuyCountById(@Param("activityThemeId") Integer activityThemeId);
}