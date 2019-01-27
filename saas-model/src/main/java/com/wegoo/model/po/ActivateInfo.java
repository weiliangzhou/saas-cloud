package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

/**
 * @author 二师兄
 */
@Data
public class ActivateInfo {
    private Integer id;

    private String merchantId;

    private String phone;

    private String realName;

    private Integer themeId;

    private String themeName;

    private Integer themePrice;

    private String referrer;

    private String referrerName;

    private String referrerPhone;

    private Integer activityId;

    private Integer isUsed;

    private String qrCodeUrl;

    private Integer sendMsg;

    private String activityCode;

    private String remark;

    private String idCardNum;

    private Date createTime;


}
