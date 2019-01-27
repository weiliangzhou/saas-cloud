package com.wegoo.saasdao.mapper;


import com.wegoo.model.po.ClassSetStatistics;

/**
 * 套课程mapper
 */
public interface ClassSetStatisticsMapper {
    /**
     * 新增
     * @return
     */
    int insert(ClassSetStatistics classSetStatistics);

    /**
     * 浏览人数+1
     * @return
     */
    int setpAddBrowseCount(Long classSetId);

    /**
     * 根据套课程id查找
     * @param classSetId
     * @return
     */
    ClassSetStatistics selectByClassSetId(Long classSetId);

}