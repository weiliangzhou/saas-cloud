package com.wegoo.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 二师兄超级帅
 * @Title: BuyResult
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1814:54
 */
@Data
public class MyUser {
    private String userId;
    private Integer dateType;
    private String phone;
    private String realName;
    private Date startDate;
    private Date endDate;
    /**
     * 0全部 1已购
     */
    private Integer queryType;
}
