package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserMember {
    private Long id;

    //用户标识
    private String userId;

    //用户等级
    private Integer memberLevel;

    //对应商户号
    private String merchantId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;
}