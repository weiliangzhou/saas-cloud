package com.wegoo.saasserviceimpl;

import com.wegoo.model.po.Certification;
import com.wegoo.model.po.UserCertification;
import com.wegoo.saasdao.mapper.UserCertificationMapper;
import com.wegoo.saasdao.mapper.UserInfoMapper;
import com.wegoo.saasdao.mapper.UserMemberMapper;
import com.wegoo.saasservice.UserCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCertificationServiceImpl implements UserCertificationService {


    @Autowired
    private UserCertificationMapper userCertificationMapper;
    @Autowired
    private UserMemberMapper userMemberMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void certification(Certification certification) {
        userCertificationMapper.insert(certification);
        userMemberMapper.update(certification.getUserId(), certification.getMerchantId());
        userInfoMapper.updateRealName(certification.getRealName(), certification.getUserId());
    }

    @Override
    public UserCertification getCertificationInfoByUserId(String userId) {
        return userCertificationMapper.getCertificationInfoByUserId(userId);
    }

    @Override
    public Integer checkIdCardNum(String idCardNum) {
        return userCertificationMapper.checkIdCardNum(idCardNum);
    }


}
