package com.wegoo.model.vo;


import com.wegoo.model.wxPayUtils.PaymentKit;
import com.wegoo.model.wxPayUtils.StrKit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxPayApiConfig
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/916:55
 */
public class WxPayH5 implements Serializable {
    private static final long serialVersionUID = -6447075676732210047L;
    private String appId;
    private String mchId;
    private String deviceInfo;
    private String nonceStr;
    private String sign;
    private String body;
    //附加参数  dy
    private String attach;
    private String outTradeNo;
    private String totalFee;
    private String spbillCreateIp;
    private String timeStart;
    private String timeExpire;
    private String notifyUrl;
    private String tradeType;
    private String openId;
    private String sceneInfo;
    private String paternerKey;

    private WxPayH5() {
    }

    public static WxPayH5 New() {
        return new WxPayH5();
    }

    public Map<String, String> build() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", this.getAppId());
        map.put("mch_id", this.getMchId());
        map.put("nonce_str", this.getNonceStr());
        map.put("body", this.getBody());
        map.put("attach", this.getAttach());
        map.put("out_trade_no", this.getOutTradeNo());
        map.put("total_fee", this.getTotalFee());
        map.put("spbill_create_ip", this.getSpbillCreateIp());
        map.put("time_start", this.getTimeStart());
        map.put("time_expire", this.getTimeExpire());
        map.put("notify_url", this.getNotifyUrl());
        map.put("trade_type", this.getTradeType());
        map.put("openid", this.getOpenId());
        map.put("scene_info", this.getSceneInfo());
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        return map;
    }

    public String getAppId() {
        if (StrKit.isBlank(this.appId)) {
            throw new IllegalArgumentException("appId 未被赋值");
        } else {
            return this.appId;
        }
    }

    public WxPayH5 setAppId(String appId) {
        if (StrKit.isBlank(appId)) {
            throw new IllegalArgumentException("appId 值不能为空");
        } else {
            this.appId = appId;
            return this;
        }
    }

    public String getMchId() {
        if (StrKit.isBlank(this.mchId)) {
            throw new IllegalArgumentException("mchId 未被赋值");
        } else {
            return this.mchId;
        }
    }

    public WxPayH5 setMchId(String mchId) {
        if (StrKit.isBlank(mchId)) {
            throw new IllegalArgumentException("mchId 值不能为空");
        } else {
            this.mchId = mchId;
            return this;
        }
    }


    public String getNonceStr() {
        if (StrKit.isBlank(this.nonceStr)) {
            this.nonceStr = String.valueOf(System.currentTimeMillis());
        }

        return this.nonceStr;
    }

    public WxPayH5 setNonceStr(String nonceStr) {
        if (StrKit.isBlank(nonceStr)) {
            throw new IllegalArgumentException("nonceStr 值不能为空");
        } else {
            this.nonceStr = nonceStr;
            return this;
        }
    }

    public String getBody() {
        if (StrKit.isBlank(this.body)) {
            throw new IllegalArgumentException("body 未被赋值");
        } else {
            return this.body;
        }
    }

    public WxPayH5 setBody(String body) {
        if (StrKit.isBlank(body)) {
            throw new IllegalArgumentException("body 值不能为空");
        } else {
            this.body = body;
            return this;
        }
    }

    public String getAttach() {
        return this.attach;
    }

    public WxPayH5 setAttach(String attach) {
        if (StrKit.isBlank(attach)) {
            throw new IllegalArgumentException("attach 值不能为空");
        } else {
            this.attach = attach;
            return this;
        }
    }

    public String getOutTradeNo() {
        if (StrKit.isBlank(this.outTradeNo)) {
            throw new IllegalArgumentException("outTradeNo 未被赋值");
        } else {
            return this.outTradeNo;
        }
    }

    public WxPayH5 setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String getTotalFee() {
        if (StrKit.isBlank(this.totalFee)) {
            throw new IllegalArgumentException("totalFee 未被赋值");
        } else {
            return this.totalFee;
        }
    }

    public WxPayH5 setTotalFee(String totalFee) {
        if (StrKit.isBlank(totalFee)) {
            throw new IllegalArgumentException("totalFee 值不能为空");
        } else {
            this.totalFee = totalFee;
            return this;
        }
    }

    public String getSpbillCreateIp() {
        if (StrKit.isBlank(this.spbillCreateIp)) {
            throw new IllegalArgumentException("spbillCreateIp 未被赋值");
        } else {
            return this.spbillCreateIp;
        }
    }

    public WxPayH5 setSpbillCreateIp(String spbillCreateIp) {
        if (StrKit.isBlank(spbillCreateIp)) {
            throw new IllegalArgumentException("spbillCreateIp 值不能为空");
        } else {
            this.spbillCreateIp = spbillCreateIp;
            return this;
        }
    }

    public String getNotifyUrl() {
        if (StrKit.isBlank(this.notifyUrl)) {
            throw new IllegalArgumentException("notifyUrl 未被赋值");
        } else {
            return this.notifyUrl;
        }
    }

    public WxPayH5 setNotifyUrl(String notifyUrl) {
        if (StrKit.isBlank(notifyUrl)) {
            throw new IllegalArgumentException("notifyUrl 值不能为空");
        } else {
            this.notifyUrl = notifyUrl;
            return this;
        }
    }

    public String getTradeType() {
        if (this.tradeType == null) {
            throw new IllegalArgumentException("tradeType 未被赋值");
        } else {
            return this.tradeType;
        }
    }

    public WxPayH5 setTradeType(String tradeType) {
        if (tradeType == null) {
            throw new IllegalArgumentException("mchId 值不能为空");
        } else {
            this.tradeType = tradeType;
            return this;
        }
    }

    public String getOpenId() {
        if (StrKit.isBlank(this.openId)) {
            throw new IllegalArgumentException("openId 未被赋值");
        } else {
            return this.openId;
        }
    }

    public WxPayH5 setOpenId(String openId) {
        if (StrKit.isBlank(openId)) {
            throw new IllegalArgumentException("openId 值不能为空");
        } else {
            this.openId = openId;
            return this;
        }
    }

    public String getSceneInfo() {
        return this.sceneInfo;
    }

    public WxPayH5 setSceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
        return this;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public WxPayH5 setTimeStart(String timeStart) {
        if (StrKit.isBlank(timeStart)) {
            throw new IllegalArgumentException("openId 值不能为空");
        } else {
            this.timeStart = timeStart;
            return this;
        }
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public WxPayH5 setTimeExpire(String timeExpire) {
        if (StrKit.isBlank(timeExpire)) {
            throw new IllegalArgumentException("openId 值不能为空");
        } else {
            this.timeExpire = timeExpire;
            return this;
        }
    }

    public String getPaternerKey() {
        if (StrKit.isBlank(this.paternerKey)) {
            throw new IllegalArgumentException("paternerKey 未被赋值");
        } else {
            return this.paternerKey;
        }
    }

    public WxPayH5 setPaternerKey(String paternerKey) {
        if (StrKit.isBlank(paternerKey)) {
            throw new IllegalArgumentException("paternerKey 值不能为空");
        } else {
            this.paternerKey = paternerKey;
            return this;
        }
    }


}
