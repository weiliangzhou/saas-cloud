package com.wegoo.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Order {
    @ApiComment(value = "订单ID", sample = "201807091123zwl")
    private String orderNo;
    @ApiComment(value = "产品ID", sample = "1")
    private Long productId;
    @ApiComment(value = "产品名称", sample = "套餐A")
    private String productName;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer actualMoney;
    @ApiComment(value = "实际付款金额", sample = "980")
    private Integer actualMoneyDesc;
    @ApiComment(value = "订单金额", sample = "980")
    private Integer moneyDesc;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer money;
    @ApiComment(value = "分佣百分比", sample = "50")
    private Integer maidPercent;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer payWay;
    @ApiComment(value = "支付方式", sample = "微信")
    private String payWayDesc;
    @ApiComment(value = "产品等级", sample = "1")
    private Integer level;
    @ApiComment(value = "等级名称", sample = "学员")
    private String levelName;
    @ApiComment(value = "有效期", sample = "365")
    private Integer validityTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer orderStatus = 0;
    @ApiComment(value = "订单状态", sample = "订单状态  0待支付 1成功  -1超时关闭")
    private String orderStatusDesc;
    @RestPackIgnore
    @JSONField(serialize = false)
    private String userId;
    @RestPackIgnore
    @JSONField(serialize = false)
    private String merchantId;
    @ApiComment(value = "下单人", sample = "二师兄")
    private String realName;
    @ApiComment(value = "手机号", sample = "1234651231")
    private String phone;
    @ApiComment(value = "微信订单号", sample = "wx1564646831")
    private String paymentNo;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "支付成功时间", sample = "2018-07-05 18:00:00")
    private Date paymentTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime = new Date();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private String wxSign;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
    @ApiComment(value = "收货地址", sample = "北京三里屯")
    private String address;
    @ApiComment(value = "个人订单列表logo", sample = "北京三里屯")
    private String imageUrl;
    @ApiComment(value = "商品介绍", sample = "北京三里屯")
    private String contentText;
    @ApiComment(value = "邀请人", sample = "6546854654")
    private String referrer;
    @ApiComment(value = "邀请人姓名", sample = "张三")
    private String referrerName;
    @ApiComment(value = "邀请人电话", sample = "14725836987")
    private String referrerPhone;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date activityStartTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date activityEndTime;

    public Integer getActualMoneyDesc() {
        return this.actualMoney == null ? 0 : this.actualMoney / 100;
    }

    public Integer getMoneyDesc() {
        return this.money == null ? 0 : (this.money / 100);
    }

    public String getOrderStatusDesc() {
        switch (this.orderStatus == null ? 0 : this.orderStatus) {
            case 0:
                return "未支付";
            case 1:
                return "已发货";
            case 2:
                return "已完成";
            case -1:
                return "订单超时";
            default:
                return "";
        }


    }

    public String getPayWayDesc() {
        switch (this.payWay == null ? 0 : this.payWay) {
            case 1:
                return "微信";
            default:
                return "";
        }

    }

}