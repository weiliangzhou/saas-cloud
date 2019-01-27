package com.wegoo.model.vo.wechat;

import lombok.Data;

/**
 * <br> 网页AccessToken 特性:一次性 </br>
 * <br> 微信公众号网页授权AccessToken 跟 普通的AccessToken不同</br>
 * <br> 微信连接: https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code</br>
 */
@Data
public class WebAccessTokenVo extends WxErrorResultVo {
    //获取到的凭证
    private String access_token;
    //凭证有效时间，单位：秒
    private String expires_in;
    //用户刷新access_token
    private String refresh_token;
    //用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
    private String openid;
    //用户授权的作用域，使用逗号（,）分隔
    private String scope;
}
