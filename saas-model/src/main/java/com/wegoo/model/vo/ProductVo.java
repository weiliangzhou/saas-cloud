package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

@Data
public class ProductVo {
    @ApiComment(value = "产品ID", sample = "1")
    private Long id;
    @ApiComment(value = "产品简介（带格式）", sample = "<p>10大板块，100节课让微商创业再也没有秘密！绝对是秒杀全网的课程！")
    private String content;
    @ApiComment(value = "产品简介（不带格式）", sample = "不带格式的介绍")
    private String contentText;
    @ApiComment(value = "产品图片", sample = "www.xiaochuang.oss.com")
    private String imageUrl;
    @ApiComment(value = "产品价格", sample = "单位：分")
    private Integer price;
    @ApiComment(value = "产品价格", sample = "单位：元")
    private String priceDesc;
    @ApiComment(value = "产品名称", sample = "99元套餐A")
    private String productName;
    @ApiComment(value = "购买人数", sample = "123000")
    private Integer buyCount;
    @ApiComment(value = "购买人数描述", sample = "12.3万")
    private String buyCountDesc;
    @ApiComment(value = "购买人数描述2", sample = "人购买")
    private String buyCountDesc2;
}
