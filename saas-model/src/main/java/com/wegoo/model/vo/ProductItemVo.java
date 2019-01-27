package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

/**
 * @author 二师兄超级帅
 * @Title: ProductItemVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2220:01
 */
@Data
public class ProductItemVo {
    @ApiComment(value = "会员等级", sample = "1")
    private Integer level;
    @ApiComment(value = "等级名称", sample = "院长")
    private String levelName;

}
