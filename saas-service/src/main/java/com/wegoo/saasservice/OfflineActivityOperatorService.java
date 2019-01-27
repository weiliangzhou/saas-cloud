package com.wegoo.saasservice;

import com.wegoo.model.po.OfflineActivityOperator;

public interface OfflineActivityOperatorService {

    OfflineActivityOperator selectByOperatorAndPassword(OfflineActivityOperator offlineActivityOperator);

    OfflineActivityOperator getOneByOperator(String operator, String merchantId);
}
