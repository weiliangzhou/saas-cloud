package com.wegoo.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ClassSetStatistics {
    private Long id;

    private Long classSetId;

    private Long browseCount;

    private Integer shareCount;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;

}