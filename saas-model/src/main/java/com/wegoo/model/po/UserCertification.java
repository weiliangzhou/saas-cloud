package com.wegoo.model.po;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

@Data
public class UserCertification {
    private Long id;

    @ApiComment(value = "真实姓名", sample = "张三")
    private String realName;

    @ApiComment(value = "身份证号", sample = "21984651984659854")
    private String idCardNum;

    @ApiComment(value = "手机号码", sample = "15516520771")
    private String phone;

    @ApiComment(value = "用户Id", sample = "ec28f6b9183f498eb9153f1593151aca")
    private String userId;

    @ApiComment(value = "商户id", sample = "0571XUDONGYA")
    private String merchantId;

    @ApiComment(value = "性别", sample = "0")
    private Integer sex;

    @ApiComment(value = "城市", sample = "浙江杭州")
    private String city;

    @ApiComment(value = "职业", sample = "技术")
    private Integer profession;

    @ApiComment(value = "创建时间", sample = "2018-7-11 16:41:43")
    private Date createTime;

    @ApiComment(value = "修改时间", sample = "2018-7-11 16:41:55")
    private Date modifyTime;

    @ApiComment(value = "可用与否", sample = "1")
    private Integer available = 1;


}