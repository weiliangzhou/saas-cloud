package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BusinessException;
import com.wegoo.saasdao.mapper.OrderMapper;
import com.wegoo.model.po.Order;
import com.wegoo.saasservice.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OrderServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/920:52
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getOrderList(Order order) {
        return orderMapper.getOrderList(order);
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order findOrderByOrderNo(String OrderNo) {
        return orderMapper.findOrderByOrderNo(OrderNo);
    }

    @Override
    public void updateOrderSetOverTime() {
        orderMapper.updateOrderSetOverTime();
    }

    @Override
    public Integer getUserOrderCount(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            throw new BusinessException("商户号不能为空");
        }
        Integer count = orderMapper.getUserOrderCount(userId, merchantId);
        return count;
    }

    @Override
    public Integer getUserBindOrderCount(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            throw new BusinessException("商户号不能为空");
        }
        Integer count = orderMapper.getUserBindOrderCount(userId, merchantId);
        return count;
    }

    @Override
    public Order getOrderByUserIdAndProductId(String userId, Long productId, String merchantId) {
        return orderMapper.getOrderByUserIdAndProductId(userId,productId,merchantId);
    }


}
