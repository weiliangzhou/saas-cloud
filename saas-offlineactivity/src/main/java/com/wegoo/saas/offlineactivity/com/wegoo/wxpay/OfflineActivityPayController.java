package com.wegoo.saas.offlineactivity.com.wegoo.wxpay;

import com.wegoo.model.wxPayUtils.PaymentKit;
import com.wegoo.saasservice.WxPayService;
import com.wegoo.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityPayController
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1920:00
 */
@Slf4j
@RestController
@RequestMapping("/qudao-offlineactivity/saas/offlineActivity")
public class OfflineActivityPayController {
    @Autowired
    private WxPayService wxPayService;

    @RequestMapping(value = "/pay_notify.do", method = {RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String pay_notify(HttpServletRequest request) {
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        try {
            String xmlMsg = HttpKit.readData(request);
            log.info("微信支付回调结果" + xmlMsg);
            Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
            String result_code = params.get("result_code");
            String return_msg = params.get("return_msg");
            /////////////////////////////以下是附加参数///////////////////////////////////
//        商家数据包
            String attach = params.get("attach");
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");
            // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
            // 避免已经成功、关闭、退款的订单被再次更新
            //  WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig());
            // if (PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())) {
//            System.out.println(("SUCCESS").equals(result_code));
            if (("SUCCESS").equals(result_code)) {
                //更新订单信息
                log.info("原订单信息:" + attach);
                String appid = params.get("appid");
                //商户号
                String mch_id = params.get("mch_id");
                String sign = params.get("sign");
                String openid = params.get("openid");
                // 总金额
                String total_fee = params.get("total_fee");
                //现金支付金额
                String cash_fee = params.get("cash_fee");
                // 微信支付订单号
                String transaction_id = params.get("transaction_id");
                // 商户订单号
                String out_trade_no = params.get("out_trade_no");
                // 支付完成时间，格式为yyyyMMddHHmmss
                String time_end = params.get("time_end");
                log.info("商户订单号：" + out_trade_no);
                //根据订单号（xx） 区分 是否是线下活动
                //--------------------------------------------线下活动----------------------------------------
                wxPayService.cxXXPayNotify(params, out_trade_no, sign, mch_id, total_fee, time_end, transaction_id);
                //发送通知等
                Map<String, String> xml = new HashMap<String, String>();
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                log.info("微信回调成功");
                return PaymentKit.toXml(xml);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //发送通知等
            Map<String, String> xml = new HashMap<String, String>();
            xml.put("return_code", "ERROR");
            xml.put("return_msg", "ERROR");
            log.error("微信回调失败", e);
            return PaymentKit.toXml(xml);
        }
        return null;
    }
}
