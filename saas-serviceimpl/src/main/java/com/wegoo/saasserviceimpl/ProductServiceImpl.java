package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.*;
import com.wegoo.model.vo.BuyResult;
import com.wegoo.saasdao.mapper.OrderMapper;
import com.wegoo.saasdao.mapper.ProductMapper;
import com.wegoo.saasservice.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:15
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserReferrerService userReferrerService;


    @Override
    public List getProductList(String merchantId) {
        return productMapper.getProductList(merchantId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BuyResult newH5Buy(Product product) {
        String userId = product.getUserId();
        log.info("开始生成订单================================>params::" + product.toString());
        User user = userService.getUserByUserId(userId);
        UserReferrer userReferrer = userReferrerService.getUserReferrerByUserIdAndMerchant(userId, product.getMerchantId());
        UserInfo userReferrerInfo = null;
        if (userReferrer != null) {
            userReferrerInfo = userInfoService.getUserInfoByUserId(userReferrer.getReferrer());
        }
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        Merchant merchant = merchantService.getMerchantByMerchantId(product.getMerchantId());
        if (merchant == null) {
            BSUtil.isTrue(Boolean.FALSE, "商户号不存在:" + merchant.getMerchantId());
        }
        String appid = merchant.getAppId();
        //查询产品信息
        //生成订单(订单号使用 年月日时分秒+mch_no+userId（自增的Id）)
        //生成订单操作日志流水表
        SimpleDateFormat sdf_yMdHm = new SimpleDateFormat("yyyyMMddHHmm");
        String merchantId = product.getMerchantId();
        //获取userId的自增Id
        Long userLongId = user.getId();
        Long productId = product.getId();
        Product localProduct = productMapper.selectByPrimaryKey(productId);
        String productName = localProduct.getProductName();
        Integer price = localProduct.getPrice();
        //通过查询saas_order表  order_status=1 userId  productId merchantId
        //如果存在 则返回 重复购买
        Order buyOrder = orderMapper.getOrderByUserIdAndProductId(userId, productId, merchantId);
        if (buyOrder != null) {
            BSUtil.isTrue(false, "已经购买过该课程啦！");
        }
        String orderNo = sdf_yMdHm.format(new Date()) + merchantId + userLongId + productId;
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setProductId(productId);
        order.setLevel(0);
        order.setProductName(productName);
        order.setMaidPercent(0);
        order.setActualMoney(price);
        order.setMoney(price);
        order.setUserId(userId);
        order.setMerchantId(merchantId);
        order.setRealName(userInfo.getRealName());
        order.setPhone(user.getRegisterMobile());
        order.setOrderStatus(0);
        if (userReferrerInfo != null) {
            order.setReferrer(userReferrerInfo.getUserId());
            order.setReferrerName(userReferrerInfo.getRealName());
            order.setReferrerPhone(userReferrerInfo.getRegisterMobile());
        }
        log.info("订单数据" + order);
        try {
            orderMapper.insertSelective(order);
        } catch (Exception e) {
            e.printStackTrace();
            BSUtil.isTrue(false, "系统繁忙,请稍后重试！", e);
        }
        BuyResult buyResult = new BuyResult();
        buyResult.setOrderNo(orderNo);
        buyResult.setTotalFee(price);
        buyResult.setTotalFeeDesc(price / 100 + "");
        buyResult.setMerchantId(merchantId);
        log.info("订单完成返回结果" + buyResult);
        return buyResult;
    }

    @Override
    public int updateBuyCountById(Long productId, String merchantId) {
        if (productId == null) {
            BSUtil.isTrue(false, "请输入要修改的商品编号");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "请输入商户号");
        }
        return productMapper.updateBuyCountById(productId, merchantId);
    }

    @Override
    public Integer getProductPriceByProductId(Long id) {
        return productMapper.getProductPriceByProductId(id);
    }

}
