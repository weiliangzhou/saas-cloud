package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {
    public User selectOneByParams(User user);

    void insertSelective(User user);

    @Update("update saas_user set register_mobile =#{phone} where user_id = #{userId}")
    void updatePhoneByUserId(@Param("phone") String phone, @Param("userId") String userId);
}