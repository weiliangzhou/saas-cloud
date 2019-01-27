package com.wegoo.saasservice;

import com.wegoo.model.po.ClassInfoStatistics;

public interface ClassInfoStatisticsService {
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
    ClassInfoStatistics getByClassInfoId(Long classInfoId);
}
