package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.po.OfflineActivity;
import lombok.Data;

@Data
public class OfflineActivityVo extends  OfflineActivity {

    @ApiComment(value = "是否为原来活动", sample = "1：是；0：不是")
    private Integer flag;

    @ApiComment(value = "主题名称", sample = "123")
    private String activityThemeName;
}