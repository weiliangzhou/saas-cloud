package com.wegoo.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ClassInfoComment {
    @ApiComment(value = "观后感ID", sample = "1")
    private Integer id;
//    @NotBlank(message = "用户id不能为空", groups = {Update.class})
    @ApiComment(value = "用户id", sample = "6a536a414e634d4c98e2095c01ff441d")
    private String userId;
//    @NotNull(message = "节课id不能为空", groups = {Update.class})
    @ApiComment(value = "节课id", sample = "1")
    private Long classInfoId;
//    @NotBlank(message = "用户昵称不能为空", groups = {Update.class})
    @ApiComment(value = "用户昵称", sample = "我是一只猪")
    private String nickname;
//    @NotBlank(message = "评论内容不能为空", groups = {Update.class})
    @ApiComment(value = "评论内容", sample = "值得学习的课堂, 下次还想再去线下听东遥老师的课程")
    private String comment;
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
//    @NotBlank(message = "商户号不能为空", groups = {Update.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
}