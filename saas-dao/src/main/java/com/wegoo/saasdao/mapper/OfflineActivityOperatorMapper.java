package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.OfflineActivityOperator;
import org.apache.ibatis.annotations.Param;

public interface OfflineActivityOperatorMapper {
    OfflineActivityOperator selectByOperatorAndPassword(OfflineActivityOperator offlineActivityOperator);
    OfflineActivityOperator getOneByOperator(@Param("operator") String operator,@Param("merchantId") String merchantId);
}