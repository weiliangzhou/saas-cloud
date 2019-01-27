package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.Certification;
import com.wegoo.model.po.UserCertification;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserCertificationMapper {

    int insert(Certification certification);

    UserCertification getCertificationInfoByUserId(@Param("userId")String userId);

    @Select("select count(*) from saas_user_certification where id_card_num = #{idCardNum}")
    Integer checkIdCardNum(@Param("idCardNum") String idCardNum);

}