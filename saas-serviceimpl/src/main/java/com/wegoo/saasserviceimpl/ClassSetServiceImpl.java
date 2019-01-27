package com.wegoo.saasserviceimpl;

import com.wegoo.model.po.ClassInfo;
import com.wegoo.model.po.ClassSet;
import com.wegoo.model.vo.ClassSetItemVo;
import com.wegoo.model.vo.ClassVo;
import com.wegoo.saasdao.mapper.ClassInfoMapper;
import com.wegoo.saasdao.mapper.ClassSetMapper;
import com.wegoo.saasservice.ClassSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("All")
@Service
public class ClassSetServiceImpl implements ClassSetService {
    @Autowired
    private ClassSetMapper classSetMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Override
    public ClassSet getClassSetByClassSetId(Long id) {
        return classSetMapper.getClassSetByClassSetId(id);
    }

    @Override
    public List<ClassVo> getClassSetList(String merchantId, Integer queryType) {
        return classSetMapper.getClassSetList(merchantId,queryType);
    }
}
