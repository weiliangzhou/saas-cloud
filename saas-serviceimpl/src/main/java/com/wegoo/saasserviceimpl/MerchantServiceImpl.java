package com.wegoo.saasserviceimpl;

import com.wegoo.constants.Available;
import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.Merchant;
import com.wegoo.saasdao.mapper.MerchantMapper;
import com.wegoo.saasservice.MerchantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public Merchant getMerchantByMerchantId(String mechantId) {
        if (StringUtils.isBlank(mechantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        Merchant merchant = new Merchant();
        merchant.setMerchantId(mechantId);
        merchant.setAvailable(Available.NORMAL.ordinal());
        return merchantMapper.selectBySelective(merchant);
    }
}
