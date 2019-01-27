package com.wegoo.model.vo;

import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: BuyResult
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1814:54
 */
@Data
public class BuyResult {
    private String orderNo;
    private String openId;
    private Integer totalFee;
    private String totalFeeDesc;
    private String merchantId;
    private String appid;
}
