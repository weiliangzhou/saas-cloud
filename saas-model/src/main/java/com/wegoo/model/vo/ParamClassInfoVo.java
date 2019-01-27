package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.po.ClassInfo;
import lombok.Data;

@Data
public class ParamClassInfoVo extends ClassInfo {
    @ApiComment(value = "时长分", sample = "10")
    private Integer minute;
    @ApiComment(value = "时长秒", sample = "10")
    private Integer second;
}
