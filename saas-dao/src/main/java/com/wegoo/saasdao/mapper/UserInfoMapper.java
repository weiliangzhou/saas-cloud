package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.UserInfo;
import com.wegoo.model.vo.MyUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserInfoMapper {
    int insertSelective(UserInfo userInfo);

    UserInfo selectBySelective(UserInfo userInfo);

    int updateByPrimaryKeySelective(UserInfo record);

    @Update("update saas_user_info set real_name = #{realName} where user_id = #{userId}")
    void updateRealName(@Param("realName") String realName, @Param("userId") String userId);

    List<UserInfo> getMyUserList(MyUser myUser);

    UserInfo getReferrerInfoByUserId(@Param("userId") String userId, @Param("merchantId") String merchantId);

    int updateIdCardNumAndRealNameAndPhoneByUserId(@Param("userId") String userId, @Param("idCardNum") String idCardNum, @Param("realName") String realName, @Param("phone") String phone);

    void updateConsultPhoneAndWechatNoAndShowTypeByUserId(@Param("consultPhone") String consultPhone, @Param("consultWechatNo") String consultWechatNo, @Param("consultShowType") Integer consultShowType, @Param("userId") String userId);

    @Update("update saas_user_info set real_name = #{realName} , register_mobile =#{phone} where user_id = #{userId}")
    void updateRegisterMobileAndRealNameByUserId(@Param("phone") String phone, @Param("realName") String realName, @Param("userId") String userId);

    void updateNickNameAndLogoByUserId(@Param("nickName") String nickName, @Param("logoUrl") String logoUrl, @Param("userId") String userId);

    @Update("update saas_user_info set real_name = #{realName} where user_id = #{userId}")
    void updateRealNameByUserId(@Param("realName") String realName, @Param("userId") String userId);

    List<UserInfo> getMyBuyUserList(MyUser myUser);
}