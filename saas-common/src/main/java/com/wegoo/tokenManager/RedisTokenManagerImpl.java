package com.wegoo.tokenManager;

import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.utils.Des3;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 二师兄超级帅
 * @Title: TokenManager
 * @ProjectName parent
 * @Description: 通过Redis存储和验证token的实现类
 * @date 2018/7/615:26
 */
@Service
public class RedisTokenManagerImpl implements TokenManager {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public TokenModel createToken(String userId) {
        // 通过userId获取redis缓存
        // 如果存在则直接获取
        // 如果不存在则创建一个
        TokenModel model;
        String token_redis = stringRedisTemplate.boundValueOps(userId).get();
        if (StringUtils.isBlank(token_redis)) {
            // 使用uuid作为源token
            String token = UUID.randomUUID().toString().replace("-", "");
            model = new TokenModel(userId, token);
            // 存储到redis并设置过期时间
            stringRedisTemplate.boundValueOps(userId).set(token, 30, TimeUnit.MINUTES);
        } else {
            model = new TokenModel(userId, token_redis);
        }
        //使用token作为key 存放User对象
        return model;
    }

    @Override
    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String token_a = Des3.decryptMode(authentication);
        if (StringUtils.isBlank(token_a)) {
            return null;
        }
        String[] param = token_a.split("_");
        if (param.length != 2) {
            return null;
        }
        // 使用userId和源token简单拼接成的token，可以增加加密措施
        String userId = param[0];
        String token = param[1];
        return new TokenModel(userId, token);
    }

    public String getUserId(String authentication) {
        if (StringUtils.isBlank(authentication)) {
            return null;
        }
        TokenModel tokenModel = this.getToken(authentication);
        if (tokenModel == null) {
            return null;
        }
        return tokenModel.getUserId();
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = stringRedisTemplate.boundValueOps(model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
//        stringRedisTemplate.boundValueOps(model.getUserId()).expire(30, TimeUnit.DAYS);
        return true;
    }

    public boolean checkFeginToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = stringRedisTemplate.boundValueOps(model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        CurrentUserInfoContext.setUserID(model.getUserId());
        // 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
//        stringRedisTemplate.boundValueOps(model.getUserId()).expire(30, TimeUnit.DAYS);
        return true;
    }

    @Override
    public void deleteToken(String userId) {
        stringRedisTemplate.delete(userId);
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
