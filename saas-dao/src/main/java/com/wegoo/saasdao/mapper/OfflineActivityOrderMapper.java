package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.OfflineActivityOrder;
import com.wegoo.model.vo.OfflineActivityOrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface OfflineActivityOrderMapper {
    int insertSelective(OfflineActivityOrder record);

    OfflineActivityOrder selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(OfflineActivityOrder record);

    OfflineActivityOrder findOrderByActivityCode(String activityCode);

    List<OfflineActivityOrder> findOrderByUserId(@Param("userId") String userId, @Param("merchantId") String merchantId);

    @Update("update saas_offline_activity_order set order_status=1 ,payment_no =#{payment_no} ,payment_time=#{paymentTime} where order_no =#{out_trade_no} ")
    int updateStatusByOrderNo(@Param("out_trade_no") String out_trade_no, @Param("payment_no") String payment_no, @Param("paymentTime") String paymentTime);

    List<OfflineActivityOrder> findOrderByParams(OfflineActivityOrderVo offlineActivityOrder);

    @Update("update saas_offline_activity_order set activity_id =#{activityId},remark=#{remark},change_times = #{changeTimes},start_time = #{startTime},end_time = #{endTime} where order_no = #{orderNo} ")
    int changeOrder(@Param("activityId") int activityId, @Param("orderNo") String orderNo, @Param("remark") String remark, @Param("changeTimes") int changeTimes, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    //@Select("SELECT * FROM saas_offline_activity_order WHERE order_no = #{orderNo}")
    OfflineActivityOrder selectOneByOrderNo(String orderNo);

    Integer getBindUserOrderCount(@Param("userId") String userId, @Param("merchantId") String merchantId);

    Integer getUserOrderCount(@Param("userId") String userId, @Param("merchantId") String merchantId);

    @Select("select DISTINCT user_id from saas_offline_activity_order where order_status =1 and referrer =#{userId}")
    List<String> findBuyUserIdByUserId(@Param("userId") String userId);
}