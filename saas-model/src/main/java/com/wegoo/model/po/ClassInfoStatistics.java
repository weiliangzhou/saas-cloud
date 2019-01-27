package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ClassInfoStatistics {
    private Long id;

    private Long classInfoId;

    private Long listenCount;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;

}