package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.List;

/**
 * 所有课程列表vo（套课程 和 单独节课程）
 */
@Data
public class PageClassVo {
    @ApiComment("总记录数")
    private Long totalPage;
    @ApiComment("当前页")
    private Integer pageNum;
    @ApiComment(seeClass = ClassVo.class)
    private List<ClassVo> list;
}
