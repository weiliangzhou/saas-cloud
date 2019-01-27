package com.wegoo.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.wegoo.model.BigDecimalUtil.BigDecimalUtil;
import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityTheme {
    @ApiComment(value = "ID", sample = "1")
    private Integer id;
    @ApiComment(value = "活动名称", sample = "微商夜大201809期")
    private String themeName;
    @ApiComment(value = "图片地址或者视频地址", sample = "12312312")
    private String themeHrefUrl;
    @ApiComment(value = "类型 0图片 1视频", sample = "12312312")
    private Integer themeType;
    @ApiComment(value = "带格式简介", sample = "12312312")
    private String content;
    @ApiComment(value = "不带格式简介", sample = "12312312")
    private String contentText;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isRecommend;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isShow;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer merchantId;
    @ApiComment(value = "创建时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiComment(value = "更新时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
    @ApiComment(value = "收听人数", sample = "25000")
    private Integer buyCount;
    @ApiComment(value = "收听人数描述", sample = "万 人收听")
    private String buyCountDesc;
    @ApiComment(value = "收听人数前端显示", sample = "2.5")
    private String buyCountShow;
    @ApiComment(value = "时长", sample = "时长 3天2夜")
    private String activityTime;
    @ApiComment(value = "图标地址", sample = "sdgs")
    private String imgUrl;
    @ApiComment(value = "价格单位分", sample = "100")
    private Integer price;
    @ApiComment(value = "限制人数", sample = "10")
    private Integer limitCount;
    @ApiComment(value = "二维码背景图", sample = "sdgs")
    private String qrBgImg;
    @ApiComment(value = "价格单位元", sample = "100")
    private String priceDesc;
    @ApiComment(value = "完善信息表单-姓名", sample = "100")
    private Integer realNameShow;
    @ApiComment(value = "完善信息表单-手机", sample = "100")
    private Integer phoneShow;
    @ApiComment(value = "完善信息表单-品牌", sample = "100")
    private Integer ppShow;
    @ApiComment(value = "完善信息表单-职务", sample = "100")
    private Integer zyShow;
    @ApiComment(value = "完善信息表单-身份证", sample = "100")
    private Integer idCardNumShow;
    @ApiComment(value = "完善信息表单-收货地址", sample = "100")
    private Integer addressShow;


    public String getBuyCountShow() {
        if (buyCount == null) {
            return "0";
        } else if (buyCount > 10000) {
            return BigDecimalUtil.div(10000, buyCount, 2) + "万";
        } else {
            return buyCount + "";
        }
    }

    public String getBuyCountDesc() {
        return "人报名";
    }

    public String getPriceDesc() {
        return String.valueOf(BigDecimalUtil.div(100, this.price == null ? 0 : this.price, 2));
    }

}