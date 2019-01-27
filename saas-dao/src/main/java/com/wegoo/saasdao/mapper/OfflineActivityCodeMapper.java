package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.OfflineActivityCode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OfflineActivityCodeMapper {
    int insertSelective(OfflineActivityCode record);

    int updateByPrimaryKeySelective(OfflineActivityCode record);

    OfflineActivityCode getOneByActivityCode(String activityCode);

    @Update("update saas_offline_activity_code set is_used =1 where activity_code =#{activityCode} and available = 1")
    int updatePassByActivityCode(@Param("activityCode") String activityCode);

    //    @Select("select * from ss_offline_activity_code where user_id =#{userId} and activity_id=#{offlineActivityId}")
    OfflineActivityCode getOneByUserIdAndOfflineActivityId(@Param("userId") String userId, @Param("offlineActivityId") Integer offlineActivityId);

    @Select("select count(*) from saas_offline_activity_code where  user_id =#{userId} and activity_theme_id=#{themeId} ")
    Integer getBuyCountByUserIdAndThemeId(@Param("userId") String userId, @Param("themeId") Integer themeId);

    @Select("select count(*) from saas_offline_activity_code where user_id = #{userId} and available = 1 and activity_theme_id = #{activityThemeId} and merchant_id = #{merchantId}")
    Integer getAlreadyBuyCountByUserIdAndThemeId(@Param("userId") String userId, @Param("activityThemeId") Integer activityThemeId, @Param("merchantId") String merchantId);

    OfflineActivityCode getOneByActivityCodeAndIsUsed(@Param("offlineActivityCode") String offlineActivityCode, @Param("isUsed") Integer isUsed);

    @Select("select count(*) from saas_offline_activity_code where user_id = #{userId} and available = 1 and activity_theme_id = #{activityThemeId} and is_used in(0,1)")
    int getUnUsedCountByUserIdAndOfflineThemeIdAndUnUsed(@Param("userId") String userId, @Param("activityThemeId") Integer activityThemeId);

    int updateOfflineActivityCodeActivityId(@Param("activityCode") String activityCode, @Param("activityId") Integer activityId);
}