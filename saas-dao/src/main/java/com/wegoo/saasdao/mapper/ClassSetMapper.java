package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.ClassSet;
import com.wegoo.model.vo.ClassSetItemVo;
import com.wegoo.model.vo.ClassVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 套课程mapper
 */
public interface ClassSetMapper {

    /**
     * 根据id获取一个套课程详情
     * @param id
     * @return
     */
    ClassSet getClassSetByClassSetId(Long id);
    /**
     * 获取所有的课程列表
     * 包括套课程 和 单独的节课程
     * @param merchantId
     * @param queryType
     * @return
     */
    List<ClassVo> getClassSetList(@Param("merchantId") String merchantId, @Param("queryType") Integer queryType);
}