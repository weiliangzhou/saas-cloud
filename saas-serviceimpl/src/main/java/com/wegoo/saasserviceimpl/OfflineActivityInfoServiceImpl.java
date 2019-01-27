package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.ActivateInfo;
import com.wegoo.saasdao.mapper.ActivateInfoMapper;
import com.wegoo.saasservice.OfflineActivityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2710:56
 */
@Service
@Slf4j
public class OfflineActivityInfoServiceImpl implements OfflineActivityInfoService {
    @Autowired
    private ActivateInfoMapper activateInfoMapper;

    @Override
    public ActivateInfo getOneByActivityCode(String activityCode) {
        return activateInfoMapper.getOneByActivityCode(activityCode);
    }

    @Override
    public void updatePassByActivityCode(String activityCode) {
        int count = activateInfoMapper.updateIsUsedByCode(activityCode);
        if (count != 1) {
            BSUtil.isTrue(false, "签到错误，请重试！");
        }
    }
}
