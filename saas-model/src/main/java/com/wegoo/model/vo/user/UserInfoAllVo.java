package com.wegoo.model.vo.user;

import lombok.Data;

/**
 * 返回用户所有信息
 */
@Data
public class UserInfoAllVo {
    private Long id;

    // 用户编号
    private String userId;

    // 注册的手机号码
    private String registerMobile;

    // 真实姓名
    private String realName;

    // 描述
    private String nickName;

    // 头像
    private String logoUrl;

    // 微信号码
    private String weChatNo;

    // 商户号
    private String merchantId;

    // 会员等级 1:普通会员 2:认证会员 99:业务员
    private Integer memberLevel;

    // 用户第三方帐号标识符
    private String openId;
    //推荐人
    private String referrer;
    //推荐人姓名
    private String referrerName;
    //推荐人手机
    private String referrerPhone;
    // 渠道 0:网页 1:微信-H5 2: 微信-小程序 3:线下导入 4:ios 5 :android
    private Integer channelId;

    //业务员绑定的下线数量
    private Integer bindCount;

    // 用户订单数量
    private Integer orderCount;

    //用户订单数
    private Integer userOrderCount;

    /**
     * 用户在线订单数
     */
    private Integer userOnlineOrderCount;
    /**
     * 业务员邀请的用户订单数
     */
    private Integer userBindOlineOrderCount;

}
