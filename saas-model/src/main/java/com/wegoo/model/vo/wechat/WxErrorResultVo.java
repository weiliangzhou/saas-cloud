package com.wegoo.model.vo.wechat;

import lombok.Data;

/**
 * <br> 微信接口返回的错误字段 </br>
 * <br>  错误码可以去微信文档上查找对应的Code :  <a href='https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433747234'>https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433747234</a></br>
 */
@Data
public class WxErrorResultVo {
    //错误码
    private Integer errcode;
    //错误描述
    private String errmsg;
}
