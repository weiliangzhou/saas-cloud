package com.wegoo.tokenManager;

import com.alibaba.fastjson.annotation.JSONField;
import com.wegoo.utils.Des3;

/**
 * @author 二师兄超级帅
 * @Title: TokenModel
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/615:26
 */
public class TokenModel {
    // 用户userId
    @JSONField(serialize = false)
    private String userId;
    // 随机生成的uuid
    @JSONField(serialize = false)
    private String token;
    // 返回加密的数据
    private String signToken;

    public TokenModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
        this.signToken = Des3.encryptMode(userId + "_" + token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        this.signToken = signToken;
    }

}
