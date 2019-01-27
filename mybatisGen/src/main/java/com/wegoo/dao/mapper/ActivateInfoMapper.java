package com.wegoo.dao.mapper;

import com.wegoo.model.po.ActivateInfo;

public interface ActivateInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivateInfo record);

    int insertSelective(ActivateInfo record);

    ActivateInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivateInfo record);

    int updateByPrimaryKey(ActivateInfo record);
}