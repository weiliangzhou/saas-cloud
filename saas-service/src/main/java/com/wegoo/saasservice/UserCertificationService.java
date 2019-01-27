package com.wegoo.saasservice;

import com.wegoo.model.po.Certification;
import com.wegoo.model.po.UserCertification;

/**
 * @author 二师兄超级帅
 * @Title: UserService
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1011:22
 */
public interface UserCertificationService {
    void certification(Certification certification);

    UserCertification getCertificationInfoByUserId(String userId);

    /**
     * @author 任鹏亮
     * @Description: 身份证查重
     */
     Integer checkIdCardNum(String idCardNum);
}
