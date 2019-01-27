package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class Merchant {
    private Long id;

    private String merchantId;

    private String merchantName;

    private String creator;

    private String appId;

    private String appSecret;

    private String wxPayKey;

    private String gzAppId;

    private String gzAppKey;

    private String buyTemplateId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

}