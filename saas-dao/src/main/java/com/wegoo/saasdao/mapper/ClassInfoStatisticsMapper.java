package com.wegoo.saasdao.mapper;


import com.wegoo.model.po.ClassInfoStatistics;

/**
 * 节课程mapper
 */
public interface ClassInfoStatisticsMapper {
    /**
     * 新增
     * @param classInfoStatistics
     * @return
     */
    int insert(ClassInfoStatistics classInfoStatistics);
    /**
     * 浏览人数+1
     * @return
     */
    int setpAddBrowseCount(Long classInfoId);

    /**
     * 根据节课程id查找
     * @param classInfoId
     * @return
     */
    ClassInfoStatistics selectByClassInfoId(Long classInfoId);

}