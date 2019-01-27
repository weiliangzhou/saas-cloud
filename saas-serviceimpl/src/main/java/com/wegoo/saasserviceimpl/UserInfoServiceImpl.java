package com.wegoo.saasserviceimpl;

import com.wegoo.constants.Available;
import com.wegoo.constants.user.Channel;
import com.wegoo.exception.BSUtil;
import com.wegoo.exception.BusinessException;
import com.wegoo.model.po.UserInfo;
import com.wegoo.model.po.UserMember;
import com.wegoo.model.po.UserOpenId;
import com.wegoo.model.po.UserReferrer;
import com.wegoo.model.vo.MyUser;
import com.wegoo.model.vo.user.UserInfoAllVo;
import com.wegoo.saasdao.mapper.UserInfoMapper;
import com.wegoo.saasservice.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private UserOpenIdService userOpenIdService;
    @Autowired
    private UserReferrerService userReferrerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;


    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {
        verfiy(userInfo);
        userInfo.setAvailable(Available.NORMAL.ordinal());
        userInfoMapper.insertSelective(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        return userInfoMapper.selectBySelective(userInfo);
    }

    @Override
    public List<UserInfo> getMyUserList(MyUser myUser) {
        if (StringUtils.isBlank(myUser.getUserId())) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        //已购列表
        Integer queryType = myUser.getQueryType();
        if (1 == queryType) {
            return userInfoMapper.getMyBuyUserList(myUser);
        } else {
            return userInfoMapper.getMyUserList(myUser);
        }

    }

    @Override
    public Boolean updateWeChatByUserId(String userId, String weChatNo) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(weChatNo)) {
            BSUtil.isTrue(false, "微信号不能为空");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setWeChatNo(weChatNo);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        return Boolean.TRUE;
    }

    private void verfiy(UserInfo userInfo) {
        if (userInfo == null) {
            BSUtil.isTrue(false, "参数错误");
        }
        if (StringUtils.isBlank(userInfo.getUserId())) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
    }

    @Override
    public UserInfoAllVo getAllUserInfo(String userId, String merchantId, Channel channel) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户标识不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        UserInfoAllVo userInfoAllVo = new UserInfoAllVo();
        UserInfo userInfo = this.getUserInfoByUserId(userId);
        if (null != userInfo) {
            BeanUtils.copyProperties(userInfo, userInfoAllVo);
            userInfoAllVo.setWeChatNo(userInfoAllVo.getWeChatNo() == null ? "" : userInfoAllVo.getWeChatNo());
            userInfoAllVo.setRealName(StringUtils.isBlank(userInfo.getRealName()) ? "" : userInfo.getRealName());
        }
        UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
        if (null != userMember) {
            userInfoAllVo.setMemberLevel(userMember.getMemberLevel());
            userInfoAllVo.setId(userMember.getId());
        }

        UserReferrer userReferrer = userReferrerService.getUserReferrerByUserIdAndMerchant(userId, merchantId);
        if (userReferrer != null) {
            String referrerId = userReferrer.getReferrer();
            UserInfo referrerInfo = userInfoMapper.getReferrerInfoByUserId(userId, merchantId);
            if (referrerInfo != null) {
                userInfoAllVo.setReferrerName(referrerInfo.getRealName());
                userInfoAllVo.setReferrerPhone(referrerInfo.getRegisterMobile());
            }
            userInfoAllVo.setReferrer(referrerId);
        }
        if (null != channel) {
            UserOpenId userOpenId = userOpenIdService.getUserOpenIdByUserIdAndMerchantIdAndChannel(userId, merchantId,
                    channel);
            if (null != userOpenId) {
                userInfoAllVo.setOpenId(userOpenId.getOpenId());
                userInfoAllVo.setChannelId(userOpenId.getChannelId());
            }
        }
        Integer bindUserCount = userReferrerService.getUserBindCount(userId, merchantId);
        userInfoAllVo.setBindCount(bindUserCount == null ? 0 : bindUserCount);
        userInfoAllVo.setUserId(userId);
        userInfoAllVo.setMerchantId(merchantId);


        Integer onlineOrderCount = orderService.getUserOrderCount(userId, merchantId);
        userInfoAllVo.setUserOnlineOrderCount(onlineOrderCount);

        Integer onlineBindCount = orderService.getUserBindOrderCount(userId, merchantId);
        userInfoAllVo.setUserBindOlineOrderCount(onlineBindCount);
        return userInfoAllVo;
    }

    @Override
    public UserInfo getReferrerInfoByUserId(String userId, String merchantId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        return userInfoMapper.getReferrerInfoByUserId(userId, merchantId);
    }

    @Override
    public void updateIdCardNumAndRealNameAndPhoneByUserId(String userId, String idCardNum, String realName, String phone) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户标识不能为空");
        }
//        if (StringUtils.isBlank(idCardNum)) {
//            throw new BusinessException("身份证号码不能为空");
//        }
//        if (StringUtils.isBlank(realName)) {
//            throw new BusinessException("真实姓名不能为空");
//        }
        if (StringUtils.isBlank(phone)) {
            throw new BusinessException("手机号码不能为空");
        }
        userInfoMapper.updateIdCardNumAndRealNameAndPhoneByUserId(userId, idCardNum, realName, phone);
    }

    @Override
    public void updateConsultPhoneAndWechatNoAndShowTypeByUserId(String consultPhone, String consultWechatNo, Integer consultShowType, String userId) {
        userInfoMapper.updateConsultPhoneAndWechatNoAndShowTypeByUserId(consultPhone, consultWechatNo, consultShowType, userId);
    }

    @Override
    public void updateRegisterMobileAndRealNameByUserId(String phone, String realName, String userId) {
        userInfoMapper.updateRegisterMobileAndRealNameByUserId(phone, realName, userId);
    }

    @Override
    public void updateNickNameAndLogoByUserId(String nickName, String logoUr, String userId) {
        userInfoMapper.updateNickNameAndLogoByUserId(nickName, logoUr, userId);
    }

    @Override
    public void updateRealNameByUserId(String realName, String userId) {
        userInfoMapper.updateRealNameByUserId(realName, userId);
    }
}
