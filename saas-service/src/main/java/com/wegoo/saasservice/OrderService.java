package com.wegoo.saasservice;

import com.wegoo.model.po.Order;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OrderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/920:51
 */
public interface OrderService {
    List<Order> getOrderList(Order order);

    int updateOrder(Order order);

    Order findOrderByOrderNo(String OrderNo);

    /**
     * 作废超时订单
     */
    void updateOrderSetOverTime();

    /**
     * 获取用户订单量
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     */
    Integer getUserOrderCount(String userId, String merchantId);

    /**
     * 查询账号邀请人的订单数
     *
     * @param userId     邀请人
     * @param merchantId 商户号
     */
    Integer getUserBindOrderCount(String userId, String merchantId);

    /**
     * 查询该商品是否已购买
     * @param userId 用户编号
     * @param productId 商品编号
     * @param merchantId 商户号
     * @return
     */
    Order getOrderByUserIdAndProductId(String userId, Long productId, String merchantId);
}
