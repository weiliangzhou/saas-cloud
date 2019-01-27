package com.wegoo.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class UserReferrer {
    @RestPackIgnore
    @JSONField(serialize = false)
    private Long id;
    //用户编号
    private String userId;
    //上级用户
    private String referrer;
    //商户号
    private String merchantId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "130********")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "130********")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
    @ApiComment(value = "是否锁定关系", sample = "0临时1锁定")
    private Integer isBuy;

}