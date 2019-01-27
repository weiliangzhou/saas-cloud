package com.wegoo.saasservice;

import com.wegoo.model.po.OfflineActivityOrder;
import com.wegoo.model.vo.OfflineActivityOrderVo;

import java.util.Date;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityOrderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2811:08
 */
public interface OfflineActivityOrderService {
    /**
     * 查询订单信息
     *
     * @param orderNo 订单编号
     * @return 返回数据VO对象
     */
    OfflineActivityOrderVo findOrderDetailByOrderNo(String orderNo);

    OfflineActivityOrder findOrderByActivityCode(String activityCode);

    List<OfflineActivityOrderVo> findOrderByParams(OfflineActivityOrderVo offlineActivityOrderVo);

    OfflineActivityOrder findOrderByOrderNo(String out_trade_no);

    void updateStatusByOrderNo(String out_trade_no, String transaction_id, String time_end);

    int changeOrder(int activityId, String orderNo, String remark, int changeTimes, Date startTime, Date endTime);

    OfflineActivityOrder selectOneByOrderNo(String orderNo);

    /**
     * 获取用户(业务员)下线的订单数量
     *
     * @param userId     用户(业务员)
     * @param merchantId 商户号
     * @return 订单里
     */
    Integer getBindUserOrderCount(String userId, String merchantId);

    /**
     * 获取用户(认证用户)订单数
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     */
    Integer getUserOrderCount(String userId, String merchantId);

    /**
     * 修改用户订单信息
     */
    Boolean updateOfflineActivityOrderInfo(OfflineActivityOrder offlineActivityOrder);

    /**
     * 插入激活用户订单
     *
     * @param offlineActivityOrder
     */
    void insertActivateUser(OfflineActivityOrder offlineActivityOrder);

    /**
     * 找到当前推荐人下线已购列表
     * @param userId
     */
    List<String> findBuyUserIdByUserId(String userId);
}
