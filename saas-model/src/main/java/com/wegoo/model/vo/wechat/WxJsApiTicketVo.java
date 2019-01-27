package com.wegoo.model.vo.wechat;

import lombok.Data;

/**
 * 用户执行js权限 ticket
 * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
 */
@Data
public class WxJsApiTicketVo {
    //错误码
    private Integer errcode;
    //提示
    private String errmsg;
    //用于生成签名算法的值
    private String ticket;
    //过期时间
    private Integer expires_in;
}
