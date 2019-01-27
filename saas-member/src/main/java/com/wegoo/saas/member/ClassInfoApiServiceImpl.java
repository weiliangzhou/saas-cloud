package com.wegoo.saas.member;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wegoo.api.member.ClassInfoApiService;
import com.wegoo.baseservice.BaseService;
import com.wegoo.model.po.ClassInfo;
import com.wegoo.model.po.ClassInfoComment;
import com.wegoo.model.po.ClassInfoStatistics;
import com.wegoo.model.vo.ClassVo;
import com.wegoo.model.vo.PageClassInfoVo;
import com.wegoo.saasservice.ClassInfoCommentService;
import com.wegoo.saasservice.ClassInfoService;
import com.wegoo.saasservice.ClassInfoStatisticsService;
import com.wegoo.utils.CheckUtil;
import com.wegoo.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qudao-member/saas/classinfo")
public class ClassInfoApiServiceImpl extends BaseService implements ClassInfoApiService{
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private ClassInfoStatisticsService classInfoStatisticsService;

    /**
     * 根据ClassSetId获取所属的节课程列表
     * @return
     */
    @Override
    @PostMapping("/getClassInfoList")
    public String getClassInfoList(@RequestBody JSONObject jsonObject){
        Long classSetId = jsonObject.getLong("classSetId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page=PageHelper.startPage(pageNum, pageSize);
        List<ClassInfo> classInfoList = classInfoService.getClassInfoListByClassSetId(classSetId);
        List<ClassVo> listVo=new ArrayList<>();
        if(CheckUtil.isNotEmpty(classInfoList)){
            for (ClassInfo c:classInfoList) {
                ClassVo classVo = new ClassVo();
                classVo.setId(c.getId());
                classVo.setLogoUrl(c.getLogoUrl());
                classVo.setTitle(c.getTitle());
                classVo.setContentText(c.getContentText());
                classVo.setAudioUrl(c.getAudioUrl());
                classVo.setContent(c.getContent());
                listVo.add(classVo);
            }
        }
        PageClassInfoVo pageVo=new PageClassInfoVo();
        pageVo.setPageNum(pageNum);
        pageVo.setTotalPage(page.getTotal());
        pageVo.setList(listVo);
        return setSuccessResult(pageVo);
    }

    /**
     * 根据Id获取节课程
     * @return
     */
    @Override
    @PostMapping("/getClassInfoByClassInfoId")
    public String getClassInfoByClassInfoId(@RequestBody JSONObject jsonObject){
        Long id = jsonObject.getLong("id");
        addClassInfoBrowseCount(id);
        ClassInfo classInfo=classInfoService.getClassInfoByClassInfoId(id);
        return setSuccessResult(classInfo);
    }

    /**
     * 浏览人数+1
     */
    private void addClassInfoBrowseCount(Long classInfoId) {
        ClassInfoStatistics classInfoStatistics= classInfoStatisticsService.getByClassInfoId(classInfoId);
        if(null == classInfoStatistics) {
            ClassInfoStatistics temp = new ClassInfoStatistics();
            temp.setClassInfoId(classInfoId);
            temp.setListenCount(1L);
            classInfoStatisticsService.insert(temp);
        }else {
            classInfoStatisticsService.setpAddBrowseCount(classInfoId);
        }
    }
}
