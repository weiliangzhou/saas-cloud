package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.UserMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserMember record);

    UserMember selectBySelective(UserMember record);

    int updateByPrimaryKeySelective(UserMember record);

    @Update("update saas_user_member set member_level = 2 where merchant_id = #{merchantId} and user_id = #{userId}")
    int update(@Param("userId") String userId, @Param("merchantId") String merchantId);

    int updateMemberLevel(@Param("userId") String userId, @Param("merchantId") String merchantId, @Param("memberLevel") Integer memberLevel);
}