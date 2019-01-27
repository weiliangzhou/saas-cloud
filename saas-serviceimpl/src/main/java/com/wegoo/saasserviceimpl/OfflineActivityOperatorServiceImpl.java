package com.wegoo.saasserviceimpl;

import com.wegoo.model.po.OfflineActivityOperator;
import com.wegoo.saasdao.mapper.OfflineActivityOperatorMapper;
import com.wegoo.saasservice.OfflineActivityOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfflineActivityOperatorServiceImpl implements OfflineActivityOperatorService {
    @Autowired
    private OfflineActivityOperatorMapper offlineActivityOperatorMapper;
    @Override
    public OfflineActivityOperator selectByOperatorAndPassword(OfflineActivityOperator offlineActivityOperator) {
        return offlineActivityOperatorMapper.selectByOperatorAndPassword(offlineActivityOperator);
    }

    @Override
    public OfflineActivityOperator getOneByOperator(String operator, String merchantId) {
        return offlineActivityOperatorMapper.getOneByOperator(operator,merchantId);
    }
}
