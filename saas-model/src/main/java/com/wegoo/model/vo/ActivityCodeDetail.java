package com.wegoo.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

/**
 * @author 二师兄超级帅
 * @Title: ActivityCodeDetail
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/3010:11
 */
@Data
public class ActivityCodeDetail {
    @ApiComment(value = "性别", sample = "0男1女")
    private String sex;
    @ApiComment(value = "手机", sample = "1325564646")
    private String phone;
    @ApiComment(value = "真实姓名", sample = "张三")
    private String realName;
    @ApiComment(value = "身份证号码", sample = "3303821564654614")
    private String idCardNum;
    @ApiComment(value = "活动地点", sample = "义乌")
    private String activityAddress;
    @ApiComment(value = "活动开始时间", sample = "09月29日")
    @JSONField(format = "MM月dd日")
    private Date activityStartTime;
    @ApiComment(value = "活动结束时间", sample = "09月30日")
    @JSONField(format = "MM月dd日 ")
    private Date activityEndTime;
    @ApiComment(value = "状态", sample = "初次")
    private String status;
    @ApiComment(value = "等级", sample = "院长")
    private String memberLevel;
    @ApiComment(value = "头像", sample = "asdasd")
    private String logoUrl;

}
