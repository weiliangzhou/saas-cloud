package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.UserOpenId;

public interface UserOpenIdMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserOpenId record);

    UserOpenId selectBySelective(UserOpenId record);

    int updateByPrimaryKeySelective(UserOpenId record);

}