package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.Merchant;

public interface MerchantMapper {
    int insertSelective(Merchant record);

    Merchant selectBySelective(Merchant record);

    int updateByPrimaryKeySelective(Merchant record);

}