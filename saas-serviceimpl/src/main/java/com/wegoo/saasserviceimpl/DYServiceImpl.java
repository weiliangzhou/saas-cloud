package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.Merchant;
import com.wegoo.model.po.Order;
import com.wegoo.model.po.User;
import com.wegoo.model.wxPayUtils.PaymentKit;
import com.wegoo.saasservice.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: DYServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/817:22
 */
@Service
@Slf4j
public class DYServiceImpl implements DYService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserReferrerService userReferrerService;

    private SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public String payNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id) {
        Order order = orderService.findOrderByOrderNo(out_trade_no);
        //如果order状态为支付成功，则不更新
        Integer status = order.getOrderStatus();
        //判断支付金额是否与订单实际支付金额一致，不一致则返回错误，防止被恶意刷单攻击
        //判断支付回调签名是否跟发送的签名一致，不一致则返回错误，防止被恶意刷单攻击
        log.info("回调订单签名--------->" + sign);
//                根据商户号获取支付key
        Merchant merchant = merchantService.getMerchantByMerchantId(mch_id);
        boolean checkSign = PaymentKit.isWechatSign(params, merchant.getWxPayKey());
        if (!checkSign) {
            BSUtil.isTrue(false, "签名错误!");
            //发送通知等
            Map<String, String> xml = new HashMap<>();
            xml.put("return_code", "ERROR");
            xml.put("return_msg", "ERROR");
            return PaymentKit.toXml(xml);
        }
        Integer orderActualMoney_temp = order.getActualMoney();
        if (Integer.parseInt(total_fee) < orderActualMoney_temp){
            BSUtil.isTrue(false, "支付失败");
        }

        if (null == status || 1 != status) {
            Order order_t = new Order();
            order_t.setOrderNo(out_trade_no);
            try {
                order_t.setPaymentTime(sdf_yMdHms.parse(time_end));
            } catch (ParseException e) {
                log.error("格式化日期出错", e);
            }
            order_t.setPaymentNo(transaction_id);
            order_t.setOrderStatus(1);
            order_t.setPayWay(1);
            //更新订单信息
            int count = orderService.updateOrder(order_t);
            if (count != 1) {
                BSUtil.isTrue(false, "异步支付更新失败");
            }
            //购买成功之后，更新用户会员等级，等级名称，会员到期时间
            String userId = order.getUserId();
            String merchantId = order.getMerchantId();
            Long productId = order.getProductId();
            productService.updateBuyCountById(productId, merchantId);
            //购买成功之后，更新saas_user_referrer is_buy=1绑定关系
            userReferrerService.updateIsBuyByUserIdAndMerchantId(userId,merchantId);
            //发送通知等
            Map<String, String> xml = new HashMap<String, String>();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            return PaymentKit.toXml(xml);
        } else {
            Map<String, String> xml = new HashMap<String, String>();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            return PaymentKit.toXml(xml);

        }

    }

}
