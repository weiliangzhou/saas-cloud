package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.ClassInfo;
import com.wegoo.model.vo.ParamClassInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 节课程mapper
 */
public interface ClassInfoMapper {
    /**
     * 根据id获取节课程详情
     *
     * @param id
     * @return
     */
    ClassInfo getClassInfoByClassInfoId(Long id);

    /**
     * 根据ClassSetId获取节课程详情
     * @return
     */
    List<ClassInfo> getClassInfoListByClassSetId(Long classSetId);

    @Select("select logo_url from saas_class_info where class_set_id=#{id} order by modify_time desc limit 1")
    String getLogoUrlByClassSetId(@Param("id") Long id);
}