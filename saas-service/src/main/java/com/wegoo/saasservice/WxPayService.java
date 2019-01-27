package com.wegoo.saasservice;

import com.wegoo.model.vo.WxPayVo;

import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxPayService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1014:23
 */
public interface WxPayService {
    void cxXXPayNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id);
    WxPayVo pay(String realIp, String openId, String orderNo, String totalFee, String appid, String mch_id, String wxPayKey);
}
