package com.wegoo.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.wegoo.model.groups.Buy;
import com.wegoo.model.groups.H5Buy;
import com.wegoo.model.groups.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 二师兄
 */
@Data
public class Product {
    @NotNull(message = "产品ID不能为空", groups = {Update.class, Buy.class, H5Buy.class})
    @ApiComment(value = "产品ID", sample = "1")
    private Long id;
    @ApiComment(value = "会员等级", sample = "1")
    private Integer level;
    @NotBlank(message = "等级名称不能为空", groups = {Update.class})
    @ApiComment(value = "等级名称", sample = "院长")
    private String levelName;
    @NotBlank(message = "产品名称不能为空", groups = {Update.class})
    @ApiComment(value = "产品名称", sample = "套餐a")
    private String productName;
    @NotNull(message = "分佣百分比不能为空", groups = {Update.class})
    @ApiComment(value = "分佣百分比", sample = "30")
    private Integer maidPercent;
    @NotNull(message = "时效不能为空", groups = {Update.class})
    @ApiComment(value = "时效（天为单位）", sample = "50")
    private Integer validityTime;
    @NotNull(message = "产品价格不能为空", groups = {Update.class})
    @ApiComment(value = "产品价格", sample = "单位：分")
    private Integer price;
    @ApiComment(value = "产品价格", sample = "单位：元")
    private String priceDesc;
    @NotBlank(message = "商户号不能为空", groups = {Buy.class, H5Buy.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
    @RestPackIgnore
    @JSONField(serialize = false)
    @NotBlank(message = "userID不能为空", groups = {Buy.class})
    private String userId;
    @RestPackIgnore
    @JSONField(serialize = false)
    @NotBlank(message = "微信H5支付终端ip不能为空")
    private String spbillCreateIp;
    @RestPackIgnore
    @JSONField(serialize = false)
    @NotBlank(message = "手机号码")
    private String phone;
    @RestPackIgnore
    @JSONField(serialize = false)
    @NotBlank(message = "系统繁忙，请刷新页面")
    private String code;
    @ApiComment(value = "产品简介（带格式）", sample = "<p>10大板块，100节课让微商创业再也没有秘密！绝对是秒杀全网的课程！")
    private String content;
    @ApiComment(value = "产品简介（不带格式）", sample = "不带格式的介绍")
    private String contentText;
    @ApiComment(value = "产品图片", sample = "www.xiaochuang.oss.com")
    private String imageUrl;
    @ApiComment(value = "购买人数", sample = "98")
    private Integer buyCount;
    @ApiComment(value = "收货地址", sample = "98")
    private String address;
    @NotBlank(message = "跳转地址")
    private String redirectUrl;


}