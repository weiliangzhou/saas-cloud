package com.wegoo.model.vo.wechat;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * js api 执行权限签名
 */
@Data
public class JsApiSignatureVo {
    //JSAPI权限列表
    public static List<String> jsApiLists = new ArrayList<>();
    private String appId;
    //随机字符串
    private String nonceStr;
    //时间
    private String timestamp;
    //链接
    private String url;
    //签名
    private String signature;
    //权限列表
    private List<String> jsApiList;

    public JsApiSignatureVo() {
        this.jsApiList = jsApiLists;
    }

    static {
        jsApiLists.add("onMenuShareTimeline");
        jsApiLists.add("onMenuShareAppMessage");
        jsApiLists.add("onMenuShareQQ");
        jsApiLists.add("onMenuShareWeibo");
        jsApiLists.add("onMenuShareQZone");
        jsApiLists.add("startRecord");
        jsApiLists.add("stopRecord");
        jsApiLists.add("onVoiceRecordEnd");
        jsApiLists.add("playVoice");
        jsApiLists.add("pauseVoice");
        jsApiLists.add("stopVoice");
        jsApiLists.add("onVoicePlayEnd");
        jsApiLists.add("uploadVoice");
        jsApiLists.add("downloadVoice");
        jsApiLists.add("chooseImage");
        jsApiLists.add("previewImage");
        jsApiLists.add("uploadImage");
        jsApiLists.add("downloadImage");
        jsApiLists.add("translateVoice");
        jsApiLists.add("getNetworkType");
        jsApiLists.add("openLocation");
        jsApiLists.add("getLocation");
        jsApiLists.add("hideOptionMenu");
        jsApiLists.add("showOptionMenu");
        jsApiLists.add("hideMenuItems");
        jsApiLists.add("showMenuItems");
        jsApiLists.add("hideAllNonBaseMenuItem");
        jsApiLists.add("showAllNonBaseMenuItem");
        jsApiLists.add("closeWindow");
        jsApiLists.add("scanQRCode");
        jsApiLists.add("chooseWXPay");
        jsApiLists.add("openProductSpecificView");
        jsApiLists.add("addCard");
        jsApiLists.add("chooseCard");
        jsApiLists.add("openCard");
    }

}
