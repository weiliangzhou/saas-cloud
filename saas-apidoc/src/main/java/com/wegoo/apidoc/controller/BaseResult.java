package com.wegoo.apidoc.controller;


import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

/**
 * 统一的Json返回类
 */
@Data
public class BaseResult {
    @ApiComment(value = "code", sample = "200 500 900 ")
    private String reCode;
    @ApiComment(value = "msg", sample = "SUCCESS FAIL token失效")
    private String reMsg;
    @ApiComment(value = "data", sample = "数据")
    private Object data;
}
