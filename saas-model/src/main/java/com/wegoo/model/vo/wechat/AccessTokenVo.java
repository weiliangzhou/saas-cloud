package com.wegoo.model.vo.wechat;

import lombok.Data;

/**
 * <br> 普通 accessToken </br>
 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
 */
@Data
public class AccessTokenVo extends WxErrorResultVo {
    //获取到的凭证
    private String access_token;
    //凭证有效时间，单位：秒
    private Integer expires_in;
}
