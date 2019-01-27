package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.groups.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二师兄超级帅
 * @Title: ActivateUser
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/11/1317:16
 */
@Data
public class ActivateUser {
    @NotBlank(message = "商户号不能为空", groups = {Update.class})
    @ApiComment(value = "商户号", sample = "院长")
    private String merchantId;
    @NotBlank(message = "wechatCode不能为空", groups = {Update.class})
    @ApiComment(value = "wechatCode", sample = "code")
    private String wechatCode;
    @NotBlank(message = "手机号不能为空", groups = {Update.class})
    @ApiComment(value = "手机号", sample = "130*********")
    private String phone;
    @NotBlank(message = "验证码不能为空", groups = {Update.class})
    @ApiComment(value = "验证码", sample = "1234")
    private String msgCode;

}
