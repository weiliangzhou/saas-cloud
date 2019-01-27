package com.wegoo.model.po;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.groups.CertificationVal;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Certification {
    private Long id;
    @NotBlank(message = "真实姓名不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "真实姓名", sample = "张三")
    private String realName;
    @NotBlank(message = "身份证号不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "身份证号", sample = "21984651984659854")
    private String idCardNum;
    @NotBlank(message = "手机号码不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "手机号码", sample = "15516520771")
    private String phone;
    @NotBlank(message = "用户Id不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "用户Id", sample = "ec28f6b9183f498eb9153f1593151aca")
    private String userId;
    @ApiComment(value = "商户id", sample = "0571XUDONGYA")
    private String merchantId;
    @ApiComment(value = "性别", sample = "0")
    private Integer sex;
    @NotBlank(message = "城市不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "城市", sample = "浙江杭州")
    private String city;
    @NotBlank(message = "职业不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "职业", sample = "技术")
    private Integer profession;
    @NotBlank(message = "验证码不能为空", groups = {CertificationVal.class})
    @ApiComment(value = "验证码", sample = "1234")
    private String code;
    @ApiComment(value = "创建时间", sample = "2018-7-11 16:41:43")
    private Date createTime;
    @ApiComment(value = "修改时间", sample = "2018-7-11 16:41:55")
    private Date modifyTime;
    @ApiComment(value = "可用与否", sample = "1")
    private Integer available = 1;

}