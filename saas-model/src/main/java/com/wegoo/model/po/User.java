package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    //用户标识
    private String userId;
    //从什么渠道注册。0:H5 1:微信小程序 2:线下导入
    private Integer registerFrom;
    // 注册的手机号码
    private String registerMobile;
    //注册时间
    private Date registerTime;
    private Date modifyTime;
    private Integer available;
}