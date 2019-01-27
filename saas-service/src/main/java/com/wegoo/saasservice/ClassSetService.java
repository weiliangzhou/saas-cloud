package com.wegoo.saasservice;

import com.wegoo.model.po.ClassSet;
import com.wegoo.model.vo.ClassSetItemVo;
import com.wegoo.model.vo.ClassVo;

import java.util.List;

public interface ClassSetService {
    /**
     * 根据id获取一个套课程详情
     * @param id
     * @return
     */
    ClassSet getClassSetByClassSetId(Long id);
    /**
     * 获取所有的课程列表
     * 包括套课程 和 单独的节课程
     * 小程序调用
     * @param merchantId
     * @param queryType
     * @Parma title 搜索
     * @return
     */
    List<ClassVo> getClassSetList(String merchantId, Integer queryType);
}
