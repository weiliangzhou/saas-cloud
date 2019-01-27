package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.UserReferrer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二师兄
 */
public interface UserReferrerMapper {
    int insertSelective(UserReferrer record);

    UserReferrer selectBySelective(UserReferrer userReferrer);

    int updateByPrimaryKeySelective(UserReferrer record);

    Integer getUserBindCount(@Param("userId") String userId, @Param("merchantId") String merchantId);

    int updateIsBuyByUserIdAndMerchantId(@Param("userId") String userId, @Param("merchantId") String merchantId);

    int updateReferrerByUserIdAndMerchantId(UserReferrer userReferrer);

    @Delete("delete from  saas_user_referrer where user_id =#{userId}")
    void removeReferrer(String userId);

    void updateIsBuyAndReferrerByUserIdAndMerchantId(@Param("referrer") String referrer, @Param("userId") String userId, @Param("merchantId") String merchantId);
}