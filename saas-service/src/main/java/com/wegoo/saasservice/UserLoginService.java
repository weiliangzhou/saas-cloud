package com.wegoo.saasservice;

import com.wegoo.constants.user.Channel;
import com.wegoo.model.vo.LoginParams;
import com.wegoo.tokenManager.TokenModel;

/**
 * @author 二师兄
 */
public interface UserLoginService {
    /**
     * 用户登录及注册
     *
     * @param loginParams 登录注册相关参数
     * @return 返回TOKEN 标识符
     */
    String login(LoginParams loginParams);

    /**
     * 业务员登录及注册
     *
     * @param loginParams 登录注册相关参数
     * @return 返回TOKEN 标识符
     */
    String busLogin(LoginParams loginParams);

    /**
     * 微信登录
     *
     * @param wechatCode 微信授权code
     * @param merchantId 商户号
     * @param channel    通道
     */
    TokenModel wxLogin(String wechatCode, String merchantId, Channel channel);

    /**
     * 激活导入用户
     *
     * @return
     */
    String activateUser(String merchantId, String wechatCode, String phone, Integer channelId, Integer registerFrom);
}
