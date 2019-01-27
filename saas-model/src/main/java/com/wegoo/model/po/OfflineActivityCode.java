package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityCode {
    private Integer id;
    private String userId;
    private String activityCode;
    private Integer isUsed;
    private Integer activityThemeId;
    private Integer activityId;
    private String merchantId;
    private Date createTime;
    private Date modifyTime;
    private Integer available;
    private String qrCodeUrl;
}