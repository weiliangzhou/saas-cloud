package com.wegoo.baseservice;

import com.alibaba.fastjson.JSON;
import com.wegoo.baseresult.BaseResult;
import com.wegoo.constants.BaseResultConstants;
import org.springframework.stereotype.Component;

/**
 * Created by 二师兄超级帅 on 2018/10/13.
 */
@Component
public class BaseService {
    /**
     * 成功 没带data
     */
    public String setSuccessResult() {
        return JSON.toJSONString(new BaseResult(BaseResultConstants.HTTP_CODE_200, BaseResultConstants.HTTP_CODE_200_VALUE, null));

    }

    /**
     * 成功 带data
     */
    public String setSuccessResult(Object data) {
        return JSON.toJSONString(new BaseResult(BaseResultConstants.HTTP_CODE_200, BaseResultConstants.HTTP_CODE_200_VALUE, data));
    }

    /**
     * 成功  带信息
     */
    public String setSuccessResult(String code, String msg) {
        return JSON.toJSONString(new BaseResult(code, msg, null));
    }

    /**
     * 失败
     */
    public String setFailResult(String code, String msg) {
        return JSON.toJSONString(new BaseResult(code, msg, null));

    }

}
