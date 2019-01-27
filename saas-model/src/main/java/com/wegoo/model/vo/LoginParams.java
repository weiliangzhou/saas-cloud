package com.wegoo.model.vo;

import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: LoginParams
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1015:37
 */
@Data
public class LoginParams {
    //手机号码
    private String phone;
    //短信验证码
    private String msgCode;
    /**
     * -----  手机号码 短信验证码 登录的时候只要这两个参数 下面的这些参数 是在用户第一次登录给与注册时使用
     */
    //注册方式 0:网页 1:微信-H5 2: 微信-小程序 3:线下导入 4:ios 5 :android
    private Integer registerFrom;
    //关联第三方 0:网页 1:微信-H5 2: 微信-小程序 3:线下导入 4:ios 5 :android
    private Integer channelId;
    //微信授权code 用于获取用户信息
    private String wechatCode;
    //商户号
    private String merchantId;
    //会员等级
    private Integer memberLevel;
    //用户真实姓名
    private String realName;
    //推荐人id
    private String referrer;
}
