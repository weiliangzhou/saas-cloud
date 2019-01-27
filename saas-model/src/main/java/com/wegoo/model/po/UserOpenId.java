package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserOpenId {
    private Long id;

    //用户标识
    private String userId;

    //第三方账号标识
    private String openId;

    //第三方
    private Integer channelId;

    //商户号
    private String merchantId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

}