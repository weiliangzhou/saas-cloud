package com.wegoo.saasservice;

import com.wegoo.constants.user.Channel;
import com.wegoo.model.po.UserOpenId;

public interface UserOpenIdService {
    /**
     * 添加第三方关联通道
     */
    UserOpenId addUserOpenId(UserOpenId userOpenId);

    /**
     * 查询用户关联的OPENID
     */
    UserOpenId getUserOpenIdByUserIdAndMerchantIdAndChannel(String userId, String merchantId, Channel channel);

    /**
     * 通过OPENID 查询用户OPENID
     *
     * @param openId     查询的OPENID
     * @param merchantId 商户号
     * @param channel    通道
     */
    UserOpenId getUserOpenIdByOpenIdAndMerchantIdAndChannel(String openId, String merchantId, Channel channel);
}
