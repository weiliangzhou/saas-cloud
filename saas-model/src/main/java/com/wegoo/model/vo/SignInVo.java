package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.groups.Buy;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二师兄超级帅
 * @Title: 线下活动签到
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2710:42
 */
@Data
public class SignInVo {
    @NotBlank(message = "签到码不能为空", groups = {Buy.class})
    @ApiComment(value = "签到code", sample = "yw20180901")
    private String activityCode;
    @NotBlank(message = "操作员不能为空", groups = {Buy.class})
    @ApiComment(value = "操作员", sample = "weigu")
    private String operator;
    @ApiComment(value = "商户号", sample = "1509688041")
    private String merchantId;

}
