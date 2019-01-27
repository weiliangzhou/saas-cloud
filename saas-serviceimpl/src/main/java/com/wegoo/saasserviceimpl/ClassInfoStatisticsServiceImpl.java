package com.wegoo.saasserviceimpl;

import com.wegoo.model.po.ClassInfoStatistics;
import com.wegoo.saasdao.mapper.ClassInfoStatisticsMapper;
import com.wegoo.saasservice.ClassInfoStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class ClassInfoStatisticsServiceImpl implements ClassInfoStatisticsService {
    @Autowired
    private ClassInfoStatisticsMapper classInfoStatisticsMapper;

    @Override
    public int insert(ClassInfoStatistics classInfoStatistics) {
        return classInfoStatisticsMapper.insert(classInfoStatistics);
    }

    @Override
    public int setpAddBrowseCount(Long classInfoId) {
        return classInfoStatisticsMapper.setpAddBrowseCount(classInfoId);
    }

    @Override
    public ClassInfoStatistics getByClassInfoId(Long classInfoId) {
        return classInfoStatisticsMapper.selectByClassInfoId(classInfoId);
    }
}
