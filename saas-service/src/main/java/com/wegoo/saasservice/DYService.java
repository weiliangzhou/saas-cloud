package com.wegoo.saasservice;

import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: 东遥回调体系
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/817:20
 */
public interface DYService {
    String payNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id);

//    String xxPayNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id);
}
