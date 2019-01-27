package com.wegoo.model.vo;

import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: WxPayVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1810:49
 */
@Data
public class WxPayVo {
    private String timeStamp;
    private String nonceStr;
    private String packageStr;
    private String paySign;
    private String signType;
    private String mweb_url;
    private String appid;
    private String orderNo;
}
