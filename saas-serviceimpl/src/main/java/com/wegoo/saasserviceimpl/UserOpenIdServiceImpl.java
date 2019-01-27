package com.wegoo.saasserviceimpl;

import com.wegoo.constants.user.Channel;
import com.wegoo.exception.BSUtil;
import com.wegoo.exception.BusinessException;
import com.wegoo.model.po.UserOpenId;
import com.wegoo.saasdao.mapper.UserOpenIdMapper;
import com.wegoo.saasservice.UserOpenIdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOpenIdServiceImpl implements UserOpenIdService {
    @Autowired
    private UserOpenIdMapper userOpenIdMapper;

    @Override
    public UserOpenId addUserOpenId(UserOpenId userOpenId) {
        verfiy(userOpenId);
        userOpenIdMapper.insertSelective(userOpenId);
        return userOpenId;
    }

    @Override
    public UserOpenId getUserOpenIdByUserIdAndMerchantIdAndChannel(String userId, String merchantId, Channel channel) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        if (null == channel) {
            BSUtil.isTrue(false, "无效第三方");
        }
        UserOpenId userOpenId = new UserOpenId();
        userOpenId.setUserId(userId);
        userOpenId.setMerchantId(merchantId);
        userOpenId.setChannelId(channel.ordinal());
        return userOpenIdMapper.selectBySelective(userOpenId);
    }

    @Override
    public UserOpenId getUserOpenIdByOpenIdAndMerchantIdAndChannel(String openId, String merchantId, Channel channel) {
        if (StringUtils.isBlank(openId)) {
            throw new BusinessException("请输入要查询的OPENID");
        }
        if (StringUtils.isBlank(merchantId)) {
            throw new BusinessException("请输入要查询的商户号");
        }
        if (channel == null) {
            throw new BusinessException("请输入要查询的通道号");
        }
        UserOpenId userOpenId = new UserOpenId();
        userOpenId.setOpenId(openId);
        userOpenId.setMerchantId(merchantId);
        userOpenId.setChannelId(channel.ordinal());
        userOpenId.setAvailable(1);
        UserOpenId sysUserOpen = userOpenIdMapper.selectBySelective(userOpenId);
        return sysUserOpen;
    }

    private void verfiy(UserOpenId userOpenId) {
        if (userOpenId == null) {
            BSUtil.isTrue(false, "参数错误");
        }
        if (userOpenId.getChannelId() == null) {
            BSUtil.isTrue(false, "第三方通道不能为空");
        }
        Channel channel = Channel.getChannelById(userOpenId.getChannelId());
        if (channel == null) {
            BSUtil.isTrue(false, "无效第三方");
        }
        if (StringUtils.isBlank(userOpenId.getMerchantId())) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        if (StringUtils.isBlank(userOpenId.getUserId())) {
            BSUtil.isTrue(false, "用户表标识不能为空");
        }
    }
}
