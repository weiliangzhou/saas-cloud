package com.wegoo.saasserviceimpl;

import com.wegoo.constants.Available;
import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.UserInfo;
import com.wegoo.model.po.UserMember;
import com.wegoo.model.po.UserReferrer;
import com.wegoo.model.vo.user.UserInfoAllVo;
import com.wegoo.saasdao.mapper.UserReferrerMapper;
import com.wegoo.saasservice.UserInfoService;
import com.wegoo.saasservice.UserMemberService;
import com.wegoo.saasservice.UserReferrerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserReferrerServiceImpl implements UserReferrerService {
    @Autowired
    private UserReferrerMapper userReferrerMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserMemberService userMemberService;

    @Override
    public UserReferrer bindUserRef(String userId, String userRef, String merchant) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(userRef)) {
            BSUtil.isTrue(false, "绑定的上级帐号不能为空");
        }
        if (userId.equals(userRef)) {
            //前端只调用一次
            // BSUtil.isTrue(false, "用户自身不能成为自身的推荐人");
            return new UserReferrer();
        }
        if (StringUtils.isBlank(merchant)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        UserReferrer sysUserRef = this.getUserReferrerByUserIdAndMerchant(userId, merchant);
        if (sysUserRef != null) {
            //BSUtil.isTrue(false, "账号已有绑定的上级关系");
            return new UserReferrer();
        }
        //获取上级用户信息验证上级是否是业务员
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userRef, merchant);
        if (null == userMember || !(userMember.getMemberLevel().equals(99))) {
            //BSUtil.isTrue(false, "绑定用户上下级关系出错");
            return new UserReferrer();
        }
        //用户是业务员的不能绑定业务员
        UserMember userMember1 = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchant);
        if (userMember1.getMemberLevel().equals(99)) {
            return new UserReferrer();
        }
        UserReferrer userReferrer = new UserReferrer();
        userReferrer.setUserId(userId);
        userReferrer.setReferrer(userRef);
        userReferrer.setMerchantId(merchant);
        return addUserReferrer(userReferrer);
    }

    @Override
    public UserReferrer addUserReferrer(UserReferrer userReferrer) {
        verfiy(userReferrer);
        userReferrer.setAvailable(Available.NORMAL.ordinal());
        userReferrer.setCreateTime(new Date());
        userReferrer.setIsBuy(0);
        userReferrerMapper.insertSelective(userReferrer);
        return userReferrer;
    }

    @Override
    public UserReferrer getUserReferrerByUserIdAndMerchant(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        UserReferrer userReferrer = new UserReferrer();
        userReferrer.setUserId(userId);
        userReferrer.setMerchantId(merchantId);
        userReferrer = userReferrerMapper.selectBySelective(userReferrer);
        return userReferrer;
    }

    @Override
    public UserInfoAllVo getUserReferrerInfo(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        UserInfoAllVo userInfoVo = new UserInfoAllVo();
        UserReferrer userReferrer = this.getUserReferrerByUserIdAndMerchant(userId, merchantId);
        if (userReferrer == null || StringUtils.isBlank(userReferrer.getReferrer())) {
            return userInfoVo;
        }
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userReferrer.getReferrer());
        if (userInfo != null) {
            BeanUtils.copyProperties(userInfo, userInfoVo);
        }
        return userInfoVo;
    }

    @Override
    public Integer getUserBindCount(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Integer count = this.userReferrerMapper.getUserBindCount(userId, merchantId);
        return count;
    }

    @Override
    public void updateIsBuyByUserIdAndMerchantId(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        int count = this.userReferrerMapper.updateIsBuyByUserIdAndMerchantId(userId, merchantId);
        if (count == 0) {
            log.error("购买之后 锁定关系失败");
        }
    }

    @Override
    public void updateIsBuyAndReferrerByUserIdAndMerchantId(String referrer, String userId, String merchantId) {
        userReferrerMapper.updateIsBuyAndReferrerByUserIdAndMerchantId(referrer, userId, merchantId);
    }

    @Override
    public UserReferrer changeUserReferrer(String userId, String userRef, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(userRef)) {
            BSUtil.isTrue(false, "绑定的上级帐号不能为空");
        }
        if (userId.equals(userRef)) {
            //前端只调用一次
            // BSUtil.isTrue(false, "用户自身不能成为自身的推荐人");
            return new UserReferrer();
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        //获取上级用户信息验证上级是否是业务员
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userRef, merchantId);
        if (null == userMember || !(userMember.getMemberLevel().equals(99))) {
            //BSUtil.isTrue(false, "绑定用户上下级关系出错");
            return new UserReferrer();
        }
        //用户是业务员的不能绑定业务员
        UserMember userMember1 = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
        if (userMember1.getMemberLevel().equals(99)) {
            return new UserReferrer();
        }
        UserReferrer userReferrer = new UserReferrer();
        userReferrer.setUserId(userId);
        userReferrer.setReferrer(userRef);
        userReferrer.setMerchantId(merchantId);
        userReferrerMapper.updateReferrerByUserIdAndMerchantId(userReferrer);
        return userReferrer;
    }

    @Override
    public void removeReferrer(String userId) {
        userReferrerMapper.removeReferrer(userId);
    }

    private void verfiy(UserReferrer userReferrer) {
        if (userReferrer == null) {
            BSUtil.isTrue(false, "参数错误");
        }
        if (StringUtils.isBlank(userReferrer.getUserId())) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(userReferrer.getReferrer())) {
            BSUtil.isTrue(false, "用户绑定的上级编号不能为空");
        }
        if (userReferrer.getMerchantId() == null) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
    }
}
