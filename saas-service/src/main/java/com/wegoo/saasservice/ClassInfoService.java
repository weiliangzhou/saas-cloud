package com.wegoo.saasservice;

import com.wegoo.model.po.ClassInfo;
import com.wegoo.model.vo.ParamClassInfoVo;

import java.util.List;

public interface ClassInfoService {

    /**
     * 根据id获取节课程详情
     * @param id
     * @return
     */
    ClassInfo getClassInfoByClassInfoId(Long id);

    /**
     * 根据ClassSetId获取所属的节课程列表
     * @return
     */
    List<ClassInfo> getClassInfoListByClassSetId(Long classSetId);

    /**
     * 根据套课Id获取列表的展示图片，安排时间倒序
     * @param id
     * @return
     */
    String getLogoUrlByClassSetId(Long id);


}
