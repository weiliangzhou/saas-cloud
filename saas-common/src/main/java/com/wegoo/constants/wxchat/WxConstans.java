package com.wegoo.constants.wxchat;

/**
 * @author 二师兄超级帅
 * @Title:
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/915:18
 */
public class WxConstans {
    public static final String PAGEPATH = "pages/home/home";
    //    H5支付url
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //    企业付款url
    public static final String ADMIN_PAY_URL = " https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //    微信支付密钥设置
    //获取公众号全局授权，有效期为2小时，存入redis，如果redis未找到则重新发起请求
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    public static final String GET_GZH_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    //发送消息模版
    public static final String SEND_KC_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    //模版ID
    //    微信授权
    public static final String WX_AUTHORIZATION = "https://api.weixin.qq.com/sns/jscode2session";
    //    查询订单
    public static final String QUERY_WX_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";

    //    微信生成二维码
    public static final String QR_CODE = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    //    小程序发送模版消息
    public static final String SEND_XCX_MSG = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
    //    获取公众号openidList
//    http请求方式: GET（请使用https协议）
    public static final String GET_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
    /**
     * H5微信获取用户授权信息 权限为 snsapi_userinfo 才可获取到
     */
    public static final String GET_WECHAT_USERINFO = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";
    /**
     * 微信获取JS执行权限
     */
    public static final String WECHAT_JSAPI = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

}
