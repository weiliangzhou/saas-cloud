package com.wegoo.saasserviceimpl;

import com.wegoo.model.po.ClassInfo;
import com.wegoo.model.vo.ParamClassInfoVo;
import com.wegoo.saasdao.mapper.ClassInfoMapper;
import com.wegoo.saasservice.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@SuppressWarnings("All")
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Override
    public ClassInfo getClassInfoByClassInfoId(Long id) {
        return classInfoMapper.getClassInfoByClassInfoId(id);
    }

    @Override
    public List<ClassInfo> getClassInfoListByClassSetId(Long classSetId) {
        return classInfoMapper.getClassInfoListByClassSetId(classSetId);
    }

    @Override
    public String getLogoUrlByClassSetId(Long id) {
        return  classInfoMapper.getLogoUrlByClassSetId(id);
    }


}
