package com.wegoo.saasservice;

import com.wegoo.constants.user.Channel;
import com.wegoo.model.po.UserInfo;
import com.wegoo.model.vo.MyUser;
import com.wegoo.model.vo.user.UserInfoAllVo;

import java.util.List;

/**
 * @author dell
 */
public interface UserInfoService {

    /**
     * 添加用户信息
     */
    UserInfo addUserInfo(UserInfo userInfo);

    /**
     * 查询用户信息
     *
     * @param userId 用户编号
     */
    UserInfo getUserInfoByUserId(String userId);

    /**
     * 查询所有与用户相关的信息
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     * @paramchannel 账号关联的类型
     */
    UserInfoAllVo getAllUserInfo(String userId, String merchantId, Channel channel);

    List<UserInfo> getMyUserList(MyUser myUser);

    /**
     * 修改用户微信号
     *
     * @param userId   用户编号
     * @param weChatNo 微信号
     */
    Boolean updateWeChatByUserId(String userId, String weChatNo);

    /**
     * 查询推荐人信息
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     */
    UserInfo getReferrerInfoByUserId(String userId, String merchantId);


    /**
     * 修改身份证号码、真实姓名
     *
     * @param userId
     * @param idCardNum
     * @param realName
     */
    void updateIdCardNumAndRealNameAndPhoneByUserId(String userId, String idCardNum, String realName, String phone);

    /**
     * 修改咨询手机号、微信号、显示类型
     *
     * @param consultPhone
     * @param consultWechatNo
     * @param consultShowType
     * @param userId
     */
    void updateConsultPhoneAndWechatNoAndShowTypeByUserId(String consultPhone, String consultWechatNo, Integer consultShowType, String userId);

    /**
     * 更新用户手机号、姓名
     *
     * @param phone
     * @param realName
     * @param userId
     */
    void updateRegisterMobileAndRealNameByUserId(String phone, String realName, String userId);

    /**
     * 更新微信昵称头像
     *
     * @param nickName
     * @param logoUrl
     * @param userId
     */
    void updateNickNameAndLogoByUserId(String nickName, String logoUrl, String userId);

    /**
     * 更新用户真实姓名
     * @param realName
     * @param userId
     */
    void updateRealNameByUserId(String realName, String userId);


}
