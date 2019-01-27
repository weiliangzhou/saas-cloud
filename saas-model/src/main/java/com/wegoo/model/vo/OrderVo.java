package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.po.Order;
import lombok.Data;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OrderVo
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1711:04
 */
@Data
public class OrderVo {
    @ApiComment("总记录数")
    private Long totalPage;
    @ApiComment("当前页")
    private Integer pageNum;
    @ApiComment(seeClass = Order.class)
    private List<Order> orderList;
}
