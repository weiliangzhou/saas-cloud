package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    List<Order> getOrderList(Order order);

    Order findOrderByOrderNo(String orderNo);

    @Update("update saas_order set order_status = -1 where order_status= 0 ")
    void updateOrderSetOverTime();

    Integer getUserOrderCount(@Param("userId") String userId, @Param("merchantId") String merchantId);

    Integer getUserBindOrderCount(@Param("userId") String userId, @Param("merchantId") String merchantId);

    Order getOrderByUserIdAndProductId(@Param("userId")String userId, @Param("productId")Long productId, @Param("merchantId")String merchantId);
}