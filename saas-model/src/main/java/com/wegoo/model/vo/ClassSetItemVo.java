package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: ClassSetItemVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2215:59
 */
@Data
public class ClassSetItemVo {
    @ApiComment("id")
    private Long id;
    @ApiComment("标题")
    private String title;
}
