package com.wegoo.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;
@Data
public class Banner {
    @ApiComment(value = "ID", sample = "1")
    private Integer id;
    @ApiComment(value = "banner图片路径",sample = "D:\\img")
    private String imageUrl;
    @ApiComment(value = "banner跳转路径",sample = "http://www.google.com")
    private String hrefUrl;
    @ApiComment(value = "跳转类型",sample = "0不跳转 1应用内跳转 2应用外跳转")
    private Integer hrefType;
    @ApiComment(value = "banner主题",sample = "七夕活动")
    private String theme;
    @ApiComment(value = "排序号",sample = "1")
    private Integer queueNumber;
    @ApiComment(value = "说明",sample = "这个banner主要应用于七夕节活动...")
    private String description;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @ApiComment(value = "是否展示", sample = "0不展示，1展示")
    private Integer isShow;
    @ApiComment(value = "端口类型",sample = "0小程序 1、H5")
    private Integer portType;
}