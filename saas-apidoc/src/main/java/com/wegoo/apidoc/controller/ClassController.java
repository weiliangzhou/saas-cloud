package com.wegoo.apidoc.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.po.ClassInfo;
import com.wegoo.model.vo.ClassVo;
import com.wegoo.model.vo.PageClassInfoVo;
import com.wegoo.model.vo.PageClassVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api2Doc(name = "课程管理")
@RestController
public class ClassController{
    @ApiComment("课程列表")
    @RequestMapping(name = "课程列表",
            value = "/qudao-member/saas/classset/getClassSetList", method = RequestMethod.POST)
    public PageClassVo getClassSetList(@ApiComment("merchantId") String merchantId, @ApiComment("pageNum") Integer pageNum,
                                       @ApiComment("pageSize") Integer pageSize, @ApiComment("queryType 0推荐 1所有") Integer queryType) {
        PageClassVo pageClassVo = new PageClassVo();
        return pageClassVo;
    }

    @ApiComment("根据id查询套课程")
    @RequestMapping(name = "根据id查询套课程",
            value = "/qudao-member/saas/classset/getClassSetDetailByClassSetId", method = RequestMethod.POST)
    public ClassVo getClassSetByClassSetId(@ApiComment("id") Long id,@ApiComment("userId") String userId,
                                           @ApiComment("merchantId") String merchantId) {
        ClassVo classVo = new ClassVo();
        return classVo;
    }

    @ApiComment("获取套课程下的节课程列表")
    @RequestMapping(name = "获取套课程下的节课程列表",
            value = "/qudao-member/saas/classinfo/getClassInfoList", method = RequestMethod.POST)
    public PageClassInfoVo getClassInfoList(@ApiComment("classSetId") Long classSetId,
                                               @ApiComment("pageNum") Integer pageNum,
                                               @ApiComment("pageSize") Integer pageSize,
                                               @ApiComment("userId") String userId,
                                               @ApiComment("商户号") String merchantId) {
        PageClassInfoVo pageVo = new PageClassInfoVo();
        return pageVo;
    }

    @ApiComment("根据Id获取节课程")
    @RequestMapping(name = "根据Id获取节课程",
            value = "/qudao-member/saas/classinfo/getClassInfoByClassInfoId", method = RequestMethod.POST)
    public ClassInfo getClassInfoByClassInfoId(@ApiComment("id") Long id) {
        ClassInfo classInfo = new ClassInfo();
        return classInfo;
    }
}
