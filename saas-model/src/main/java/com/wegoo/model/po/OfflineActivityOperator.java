package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityOperator {
    private Integer id;
    private String operator;
    private String password;
    private Integer activityThemeId;
    private String merchantId;
    private Date createTime;
    private Date modifyTime;
    private Integer available;
}