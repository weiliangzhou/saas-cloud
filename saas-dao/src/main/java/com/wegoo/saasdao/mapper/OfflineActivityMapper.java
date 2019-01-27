package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.OfflineActivity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OfflineActivityMapper {

    int insertSelective(OfflineActivity record);

    OfflineActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfflineActivity record);

    OfflineActivity getOneByActivityIdAndCheckTime(Integer id);

    List<OfflineActivity> getOfflineActivityListByThemeId(@Param("merchantId") String merchantId, @Param("activityThemeId") Integer activityThemeId);

    @Update(" update saas_offline_activity set buy_count=buy_count+1 where id=#{activityId}")
    int updateBuyCountById(Integer activityId);

    @Update(" update saas_offline_activity set buy_count=buy_count-1 where id=#{activityId}")
    int updateOldBuyCountById(Integer activityId);

    @Select("SELECT count(*) FROM saas_offline_activity WHERE id = #{activityId} AND datediff(activity_start_time, now()) >= 7")
    int getStartTime(int activityId);

    List<OfflineActivity> getOfflineActivityChangeListByThemeId(@Param("merchantId") String merchantId, @Param("activityThemeId") Integer activityThemeId);

    List<Integer> getChangeActivityIdList();
}