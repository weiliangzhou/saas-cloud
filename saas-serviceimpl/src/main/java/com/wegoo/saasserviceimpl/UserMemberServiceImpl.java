package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.UserMember;
import com.wegoo.saasdao.mapper.UserMemberMapper;
import com.wegoo.saasservice.UserMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMemberServiceImpl implements UserMemberService {
    @Autowired
    private UserMemberMapper userMemberMapper;

    @Override
    public UserMember addUserMember(UserMember userMember) {
        verfiy(userMember);
        userMemberMapper.insertSelective(userMember);
        return userMember;
    }

    @Override
    public UserMember getUserMemberByUserIdAndMerchatId(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        UserMember userMember = new UserMember();
        userMember.setUserId(userId);
        userMember.setMerchantId(merchantId);
        return userMemberMapper.selectBySelective(userMember);
    }

    @Override
    public Boolean updateMemberLevel(String userId, String merchantId, Integer memberLevel) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        if (memberLevel == null) {
            BSUtil.isTrue(false, "用户等级不能为空");
        }
        userMemberMapper.updateMemberLevel(userId, merchantId, memberLevel);
        return Boolean.TRUE;
    }

    private void verfiy(UserMember userMember) {
        if (userMember == null) {
            BSUtil.isTrue(false, "参数错误");
        }
        if (StringUtils.isBlank(userMember.getUserId())) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (userMember.getMemberLevel() == null) {
            BSUtil.isTrue(false, "用户等级不能为空");
        }
        if (StringUtils.isBlank(userMember.getMerchantId())) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
    }
}
