package com.wegoo.saasserviceimpl;

import com.wegoo.config.PayNotifyProperties;
import com.wegoo.constants.WxConstans;
import com.wegoo.model.po.OfflineActivity;
import com.wegoo.model.po.OfflineActivityCode;
import com.wegoo.model.po.OfflineActivityOrder;
import com.wegoo.model.vo.WxPayH5;
import com.wegoo.model.vo.WxPayVo;
import com.wegoo.model.wxPayUtils.PaymentKit;
import com.wegoo.saasservice.*;
import com.wegoo.utils.HttpsUtils;
import com.wegoo.utils.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxPayServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1014:24
 */
@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private PayNotifyProperties payNotifyProperties;
    @Autowired
    private UserReferrerService userReferrerService;
    private SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyyMMddHHmmss");

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public void cxXXPayNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id) {
        //线下活动回调通知
        //生成二维码
        OfflineActivityOrder offlineActivityOrder = offlineActivityOrderService.findOrderByOrderNo(out_trade_no);
        //如果order状态为支付成功，则不更新
        Integer orderStatus = offlineActivityOrder.getOrderStatus();
        if (orderStatus == null || orderStatus == 1) {
            return;
        }
        Integer activityId = offlineActivityOrder.getActivityId();
        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(activityId);
        String userId = offlineActivityOrder.getUserId();
        //生成二维码
        log.info("开始生成二维码" + out_trade_no);
        OfflineActivityCode offlineActivityCode = new OfflineActivityCode();
        String activityCode = offlineActivityOrder.getActivityCode();
        offlineActivityCode.setActivityCode(activityCode);
        String qrCodeUrl = QRCodeUtil.createQrCode(payNotifyProperties.getQrCodeUrl() + activityCode, null, null);
        offlineActivityCode.setActivityId(offlineActivity.getId());
        offlineActivityCode.setActivityThemeId(offlineActivity.getActivityThemeId());
        offlineActivityCode.setAvailable(1);
        offlineActivityCode.setMerchantId(mch_id);
        offlineActivityCode.setUserId(userId);
        offlineActivityCode.setCreateTime(new Date());
        offlineActivityCode.setQrCodeUrl(qrCodeUrl);
        offlineActivityCode.setIsUsed(0);
        offlineActivityCodeService.insert(offlineActivityCode);
        log.info("二维码生成成功:" + qrCodeUrl);
        //更新订单状态、支付流水号、支付时间
        offlineActivityOrderService.updateStatusByOrderNo(out_trade_no, transaction_id, time_end);
        //更新活动购买人数
        offlineActivityService.updateBuyCountById(activityId);
        //更新活动主题购买人数
        offlineActivityThemeService.updateBuyCountById(offlineActivity.getActivityThemeId());
    }*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cxXXPayNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id) {
        //线下活动回调通知
        //生成二维码
        OfflineActivityOrder offlineActivityOrder = offlineActivityOrderService.findOrderByOrderNo(out_trade_no);
        //如果order状态为支付成功，则不更新
        Integer orderStatus = offlineActivityOrder.getOrderStatus();
        if (orderStatus == null || orderStatus == 1) {
            return;
        }
        Integer themeId = offlineActivityOrder.getActivityThemeId();
        String userId = offlineActivityOrder.getUserId();
        //生成二维码
        log.info("开始生成二维码" + out_trade_no);
        OfflineActivityCode offlineActivityCode = new OfflineActivityCode();
        String activityCode = offlineActivityOrder.getActivityCode();
        offlineActivityCode.setActivityCode(activityCode);
        String qrCodeUrl = QRCodeUtil.createQrCode(payNotifyProperties.getQrCodeUrl() + activityCode, null, null);
//        offlineActivityCode.setActivityId(offlineActivity.getId());
        offlineActivityCode.setActivityThemeId(themeId);
        offlineActivityCode.setAvailable(1);
        offlineActivityCode.setMerchantId(mch_id);
        offlineActivityCode.setUserId(userId);
        offlineActivityCode.setCreateTime(new Date());
        offlineActivityCode.setQrCodeUrl(qrCodeUrl);
        offlineActivityCode.setIsUsed(0);
        offlineActivityCodeService.insert(offlineActivityCode);
        log.info("二维码生成成功:" + qrCodeUrl);
        //更新订单状态、支付流水号、支付时间
        offlineActivityOrderService.updateStatusByOrderNo(out_trade_no, transaction_id, time_end);
        //更新活动购买人数
        //offlineActivityService.updateBuyCountById(activityId);
        //更新活动主题购买人数
        offlineActivityThemeService.updateBuyCountById(themeId);
        //购买成功之后，更新saas_user_referrer is_buy=1绑定关系
        userReferrerService.updateIsBuyByUserIdAndMerchantId(userId, mch_id);
    }

    @Override
    public WxPayVo pay(String realIp, String openId, String orderNo, String totalFee, String gzhAppId, String mch_id, String wxPayKey) {
        log.info("开始微信支付realIp-->" + realIp + "openId-->" + openId + "orderNo-->" + orderNo + "totalFee-->" + totalFee + "gzhAppId-->" + gzhAppId + "mch_id-->" + mch_id + "wxPayKey->" + wxPayKey);
        Calendar cal = Calendar.getInstance();
        String timeStart = sdf_yMdHms.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, +5);
        String timeExpire = sdf_yMdHms.format(cal.getTime());
        Map<String, String> params = WxPayH5.New()
                .setAppId(gzhAppId)
                .setMchId(mch_id)
                .setBody("微谷教育")
                .setOutTradeNo(orderNo)
                .setTotalFee(totalFee)
                .setTimeStart(timeStart)
                .setTimeExpire(timeExpire)
                .setNotifyUrl(payNotifyProperties.getPayNotifyUrl())
                .setSpbillCreateIp("127.0.0.1")
                .setTradeType("JSAPI")
                .setOpenId(openId)
                .setSceneInfo("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"admin.com\",\"wap_name\": \"微谷教育\"}}")
                .setAttach("微谷教育")
                .setPaternerKey(wxPayKey)
                .build();
        //获取微信返回的结果
        log.info("开始发送微信支付xml--->" + PaymentKit.toXml(params));
        String xmlResult = HttpsUtils.sendPost(WxConstans.PAY_URL, PaymentKit.toXml(params));
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.info("结束发送微信支付xml--->" + result);
        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            log.error("return_code>" + return_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            log.error("result_code>" + result_code + " return_msg>" + return_msg);
            throw new RuntimeException(return_msg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String trade_type = result.get("trade_type");
        //预支付交易会话标识
        String prepay_id = result.get("prepay_id");
        //支付跳转链接
        String mweb_url = result.get("mweb_url");
        String date = sdf_yMdHms.format(new Date());
        Map payResult = new HashMap();
        payResult.put("appId", gzhAppId);
        payResult.put("timeStamp", date);
        payResult.put("nonceStr", params.get("nonce_str"));
        payResult.put("package", "prepay_id=" + prepay_id);
        payResult.put("signType", "MD5");
        String paySign = PaymentKit.createSign(payResult, wxPayKey);
        payResult.put("paySign", paySign);
        WxPayVo wxPayVo = new WxPayVo();
        wxPayVo.setTimeStamp(date);
        wxPayVo.setNonceStr(params.get("nonce_str"));
        wxPayVo.setPackageStr("prepay_id=" + prepay_id);
        wxPayVo.setSignType("MD5");
        wxPayVo.setPaySign(payResult.get("paySign").toString());
        wxPayVo.setMweb_url(mweb_url);
        wxPayVo.setAppid(gzhAppId);
        wxPayVo.setOrderNo(orderNo);
        return wxPayVo;
    }


//    @Override
//    public void adminPay(@Validated(AdminPay.class) AdminPayVo adminPayVo) {
//        log.info("开始企业付款");
//        String openId = adminPayVo.getOpenId();
//        String realName = adminPayVo.getRealName();
//        Integer amount = adminPayVo.getAmount();
//        String desc = adminPayVo.getDesc();
//        String realIp = adminPayVo.getRealIp();
//        Map<String, String> params = WxAdminPay.New()
//                .setMch_appid(WxConstans.APPID)
//                .setMchid(WxConstans.MCHID)
//                .setPartner_trade_no("提现号")
//                .setOpenid(openId)
//                .setRe_user_name(realName)
//                .setAmount(amount)//企业付款金额，单位为分
//                .setDesc(desc)
//                .setSpbill_create_ip(realIp)
//                .setPaternerKey(WxConstans.PARTNERKEY)
//                .build();
//
//        //获取微信返回的结果
//        log.info("开始发送企业付款xml--->" + PaymentKit.toXml(params));
//        String xmlResult = HttpsUtils.sendPost(WxConstans.ADMIN_PAY_URL, PaymentKit.toXml(params)).toString();
//        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
//        log.info("结束发送企业付款xml--->" + result);
//        String return_code = result.get("return_code");
//        String return_msg = result.get("return_msg");
//        if (!PaymentKit.codeIsOK(return_code)) {
//            log.error("return_code>" + return_code + " return_msg>" + return_msg);
//            throw new RuntimeException(return_msg);
//        }
//        String result_code = result.get("result_code");
//        if (!PaymentKit.codeIsOK(result_code)) {
//            log.error("result_code>" + result_code + " return_msg>" + return_msg);
//            throw new RuntimeException(return_msg);
//        }
//        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
////        商户订单号	partner_trade_no	是	1217752501201407033233368018	String(32)	商户订单号，需保持历史全局唯一性(只能是字母或者数字，不能包含有符号)
////        微信订单号	payment_no	是	1007752501201407033233368018	String	企业付款成功，返回的微信订单号
////        微信支付成功时间	payment_time	是	2015-05-19 15：26：59	String	企业付款成功时间
//        String partner_trade_no = result.get("partner_trade_no");
//        String payment_no = result.get("payment_no");
//        String payment_time = result.get("payment_time");
//        log.info("商户订单号:" + partner_trade_no + " 企业付款成功，返回的微信订单号:" + payment_no + "企业付款成功时间" + payment_time);
//        //更新提现信息
//        int updateCount = withdrawService.updateByWithdrawId(partner_trade_no, partner_trade_no, payment_no);
//        if (0 == updateCount)
//            BSUtil.isTrue(false, "微信付款成功，更新数据库异常");
//    }

}
