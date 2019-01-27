package com.wegoo.saasserviceimpl.wechat;

import com.alibaba.fastjson.JSONObject;
import com.wegoo.constants.WxConstans;
import com.wegoo.constants.user.Channel;
import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.Merchant;
import com.wegoo.model.po.UserOpenId;
import com.wegoo.model.vo.wechat.*;
import com.wegoo.model.wxPayUtils.HashKit;
import com.wegoo.model.wxPayUtils.PaymentKit;
import com.wegoo.saasservice.MerchantService;
import com.wegoo.saasservice.UserOpenIdService;
import com.wegoo.saasservice.wechat.WechatService;
import com.wegoo.utils.HttpsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserOpenIdService userOpenIdService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public WebUserInfoVo getWxWebUserInfo(String wechatCode, String merchantId) {
        if (StringUtils.isBlank(wechatCode)) {
            BSUtil.isTrue(false, "微信授权CODE为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (null == merchant || StringUtils.isBlank(merchant.getMerchantId())) {
            BSUtil.isTrue(false, "无效商户号");
        }
        //1.通过授权code 换取 openId
        WebAccessTokenVo accessToken = this.getGzhWebAccessToken(wechatCode, merchantId);
        if (!accessToken.getErrcode().equals(200)) {
            BSUtil.isTrue(false, "微信code交换获取OpenId 出错 code:" + accessToken.getErrcode() + " 信息: " + accessToken.getErrmsg());
        }
        //2.通过openId 获取用户信息
        String requestUrl = String.format("&access_token=%s&openid=%s", WxConstans.GET_WECHAT_USERINFO, accessToken.getAccess_token(), accessToken.getOpenid());
        String resultJson = HttpsUtils.sendGet(requestUrl, null);
        WebUserInfoVo webUserInfoVo = JSONObject.parseObject(resultJson, WebUserInfoVo.class);
        return webUserInfoVo;
    }

    @Override
    public UserInfoVo getGzhUserInfo(String merchantId, String userId) {
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (null == merchant || StringUtils.isBlank(merchant.getMerchantId())) {
            BSUtil.isTrue(false, "无效商户号");
        }
        if (StringUtils.isBlank(userId)) {
            userId = CurrentUserInfoContext.getUserID();
        }
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByUserIdAndMerchantIdAndChannel(userId, merchantId, Channel.H5);
        if (null == userOpenId || StringUtils.isBlank(userOpenId.getOpenId())) {
            BSUtil.isTrue(false, "无效OPENID");
        }
        AccessTokenVo accessTokenVo = this.getCommonAccessToken(merchantId);
        String requestUrl = String.format("%s?access_token=%s&openid=%s&lang=%s", WxConstans.GZH_USER_INFO, accessTokenVo.getAccess_token(), userOpenId.getOpenId(), "zh_CN");
        String jsonReuslt = HttpsUtils.sendGet(requestUrl, null);
        UserInfoVo userInfoVo = JSONObject.parseObject(jsonReuslt, UserInfoVo.class);
        return userInfoVo;
    }

    @Override
    public WebAccessTokenVo getGzhWebAccessToken(String wechatCode, String merchantId) {
        if (StringUtils.isBlank(wechatCode)) {
            BSUtil.isTrue(false, "微信授权CODE为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (null == merchant || StringUtils.isBlank(merchant.getMerchantId())) {
            BSUtil.isTrue(false, "无效商户号");
        }
        String requestUrl = String.format("%s&appid=%s&secret=%s&code=%s", WxConstans.GET_GZH_OPENID, merchant.getGzAppId(), merchant.getGzAppKey(), wechatCode);
        String resultJson = HttpsUtils.sendGet(requestUrl, null);
        WebAccessTokenVo accessToken = JSONObject.parseObject(resultJson, WebAccessTokenVo.class);
        return accessToken;
    }

    @Override
    public AccessTokenVo getCommonAccessToken(String merchantId) {
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (null == merchant || StringUtils.isBlank(merchant.getMerchantId())) {
            BSUtil.isTrue(false, "无效商户号");
        }
        AccessTokenVo tokenVo = null;
        String accessTokenResult = stringRedisTemplate.boundValueOps("common-token_" + merchantId).get();
        if (StringUtils.isBlank(accessTokenResult)) {
            String requestUrl = String.format("%s&appid=%s&secret=%s", WxConstans.ACCESS_TOKEN, merchant.getGzAppId(), merchant.getGzAppKey());
            String resultJson = HttpsUtils.sendGet(requestUrl, null);
            //保存到缓存
            tokenVo = JSONObject.parseObject(resultJson, AccessTokenVo.class);
            if (tokenVo.getErrcode() == null || tokenVo.getErrcode().equals(200)) {
                stringRedisTemplate.boundValueOps("common-token_" + merchantId).set(resultJson, 5, TimeUnit.MINUTES);
            } else {
                BSUtil.isTrue(false, "获取微信TOKEN出错 code : " + tokenVo.getErrcode() + " 错误信息:" + tokenVo.getErrmsg());
            }
        } else {
            tokenVo = JSONObject.parseObject(accessTokenResult, AccessTokenVo.class);
        }
        return tokenVo;
    }

    @Override
    public WxJsApiTicketVo getJsApiTicket(String merchantId) {
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (null == merchant || StringUtils.isBlank(merchant.getMerchantId())) {
            BSUtil.isTrue(false, "无效商户号");
        }
        AccessTokenVo tokenVo = this.getCommonAccessToken(merchantId);
        WxJsApiTicketVo wxJsApiTicketVo = null;
        String jsTicketKey = String.format("jsapi_%s", tokenVo.getAccess_token());
        String chechResultJson = stringRedisTemplate.boundValueOps(jsTicketKey).get();
        if (StringUtils.isBlank(chechResultJson)) {
            String requesturl = String.format("%s?access_token=%s&type=jsapi", WxConstans.WECHAT_JSAPI, tokenVo.getAccess_token());
            String resultJson = HttpsUtils.sendGet(requesturl, null);
            wxJsApiTicketVo = JSONObject.parseObject(resultJson, WxJsApiTicketVo.class);
            if (wxJsApiTicketVo.getErrcode() == null || wxJsApiTicketVo.getErrcode().equals(0)) {
                stringRedisTemplate.boundValueOps(jsTicketKey).set(resultJson, 5, TimeUnit.MINUTES);
            } else {
                BSUtil.isTrue(false, "获取微信js tickent 出错 code:" + wxJsApiTicketVo.getErrcode() + " 错误信息:" + wxJsApiTicketVo.getErrmsg());
            }
        } else {
            wxJsApiTicketVo = JSONObject.parseObject(chechResultJson, WxJsApiTicketVo.class);
        }
        return wxJsApiTicketVo;
    }

    @Override
    public JsApiSignatureVo getJsApiSignatureVo(String merchantId, String url) {
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (null == merchant || StringUtils.isBlank(merchant.getMerchantId())) {
            BSUtil.isTrue(false, "无效商户号");
        }
        WxJsApiTicketVo wxJsApiTicketVo = this.getJsApiTicket(merchantId);
        if (!(wxJsApiTicketVo.getErrcode() == null || wxJsApiTicketVo.getErrcode().equals(0))) {
            BSUtil.isTrue(false, "获取JS执行权限出错");
        }
        //算法生成签名
        String noncestr = UUID.randomUUID().toString();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Map<String, String> params = new HashMap<>();
        params.put("jsapi_ticket", wxJsApiTicketVo.getTicket());
        params.put("noncestr", noncestr);
        params.put("timestamp", timestamp);
        params.put("url", url);
        String sing = PaymentKit.packageSign(params, false);
        String signature = HashKit.sha1(sing);
        //对已有的参数进行封装
        JsApiSignatureVo signatureVo = new JsApiSignatureVo();
        signatureVo.setNonceStr(noncestr);
        signatureVo.setAppId(merchant.getAppId());
        signatureVo.setTimestamp(timestamp);
        signatureVo.setUrl(url);
        signatureVo.setSignature(signature);
        return signatureVo;
    }
}
