package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.groups.Buy;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityBuy
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2811:50
 */
@Data
public class OfflineActivityBuy {
    //    activityId
    @NotNull(message = "活动id不能为空", groups = {Buy.class})
    @ApiComment(value = "活动id", sample = "001")
    @Deprecated
    private Integer activityId;

    @NotNull(message = "购买的活动编号", groups = {Buy.class})
    @ApiComment(value = "购买的活动编号", sample = "001")
    private Integer themeId;

    //    姓名
    @NotBlank(message = "真实姓名", groups = {Buy.class})
    @ApiComment(value = "realName", sample = "孙悟空")
    private String realName;
    //    性别
/*    @NotNull(message = "性别不能为空", groups = {Buy.class})
    @ApiComment(value = "sex", sample = "0 男 1女")
    private Integer sex;*/
    //    所在城市
    /*@NotBlank(message = "所在城市", groups = {Buy.class})
    @ApiComment(value = "city", sample = "所在城市")
    private String city;*/
    //    手机
    @NotBlank(message = "手机号不能为空", groups = {Buy.class})
    @ApiComment(value = "phone", sample = "13000000000")
    private String phone;
    //    身份证
    @NotBlank(message = "idCardNum", groups = {Buy.class})
    @ApiComment(value = "idCardNum", sample = "33038211111111")
    private String idCardNum;
/*    @NotBlank(message = "profession", groups = {Buy.class})
    @ApiComment(value = "profession", sample = "职业")
    private Integer profession;*/
    @ApiComment(value = "merchantId", sample = "商户号")
    private String merchantId;


}

