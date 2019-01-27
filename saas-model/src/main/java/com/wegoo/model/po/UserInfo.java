package com.wegoo.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    @RestPackIgnore
    @JSONField(serialize = false)
    private Long id;
    private String userId;
    @ApiComment(value = "注册手机号", sample = "130********")
    private String registerMobile;
    @ApiComment(value = "真实姓名", sample = "130********")
    private String realName;
    @ApiComment(value = "昵称", sample = "130********")
    private String nickName;
    @ApiComment(value = "头像", sample = "130********")
    private String logoUrl;
    @ApiComment(value = "微信号", sample = "130********")
    private String weChatNo;
    @ApiComment(value = "身份证号", sample = "130********")
    private String idCardNum;
    @JSONField(format = "yyyy-MM-dd")
    @ApiComment(value = "更新时间", sample = "130********")
    private Date modifyTime;
    @JSONField(format = "yyyy-MM-dd")
    @ApiComment(value = "创建时间", sample = "130********")
    private Date createTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
    @ApiComment(value = "推荐人手机号", sample = "130********")
    private String referrerPhone;
    @ApiComment(value = "咨询手机号", sample = "130********")
    private String consultPhone;
    @ApiComment(value = "咨询微信号", sample = "130********")
    private String consultWechatNo;
    @ApiComment(value = "推荐人手机号", sample = "0展示注册手机号 1展示咨询手机号 2展示咨询微信号")
    private Integer consultShowType;
}