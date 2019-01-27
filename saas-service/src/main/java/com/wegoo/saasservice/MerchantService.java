package com.wegoo.saasservice;

import com.wegoo.model.po.Merchant;

public interface MerchantService {

    /**
     * 通过商户号查询商户信息
     *
     * @param mechantId 商户号
     */
    Merchant getMerchantByMerchantId(String mechantId);
}
