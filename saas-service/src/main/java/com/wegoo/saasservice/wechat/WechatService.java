package com.wegoo.saasservice.wechat;

import com.wegoo.model.vo.wechat.*;

/**
 * 微信相关接口
 */
public interface WechatService {

    /**
     * 微信网页授权获取微信信息
     *
     * @param wechatCode 微信授权Code
     * @param merchantId 商户号
     */
    public WebUserInfoVo getWxWebUserInfo(String wechatCode, String merchantId);

    /**
     * 普通token获取用户信息
     * 如果不存在token 则传入userId
     * @param merchantId 商户号
     */
    public UserInfoVo getGzhUserInfo(String merchantId, String userId);

    /**
     * 获取网页授权的AccessToken
     * 通过用户授权的code 获取用户的ACCESS_TOKEN
     *
     * @param wechatCode 微信授权cdoe
     * @param merchantId 商户号
     */
    public WebAccessTokenVo getGzhWebAccessToken(String wechatCode, String merchantId);

    /**
     * 获取普通的AccessToken
     *
     * @param merchantId 商户号
     */
    public AccessTokenVo getCommonAccessToken(String merchantId);

    /**
     * 查询微信JSAPI ticket
     *
     * @param merchantId 商户号
     */
    public WxJsApiTicketVo getJsApiTicket(String merchantId);

    /**
     * 获取JSAPI 签名
     *
     * @param merchantId 商户号
     * @param url        url
     */
    public JsApiSignatureVo getJsApiSignatureVo(String merchantId, String url);

}
