package com.wegoo.saas.offlineactivity.FB;

import com.alibaba.fastjson.JSONObject;
import com.wegoo.model.po.Certification;
import com.wegoo.model.vo.ActivateUser;
import com.wegoo.model.vo.LoginParams;
import com.wegoo.saas.offlineactivity.feginService.UserFeignService;
import org.springframework.stereotype.Component;

/**
 * Created by 二师兄超级帅 on 2018/4/12.
 */
@Component
public class UserFB implements UserFeignService {


    @Override
    public String certification(Certification certification) {
        return null;
    }

    @Override
    public String loginAndRegister(LoginParams loginParams) {
        return null;
    }

    @Override
    public String login(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String busLogin(LoginParams loginParams) {
        return null;
    }



    @Override
    public String sendRegisterCode(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String bindUserReferrer(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getUserOpenIdByUserIdAndMerchantIdAndChannel(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getUserMemberByUserIdAndMerchatId(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getUserInfoByUserId() {
        return null;
    }

    @Override
    public String getAllUserInfo(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getCertificationInfoByUserId(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getMyUserList(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getReferrerInfoByUserId(JSONObject jsonObject) {
        return null;
    }


    @Override
    public String getGzhUserInfo(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getUserMemberByUserIdAndMerchantIdWithoutToken(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getUserInfoByUserIdWithoutToken(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getOrderList(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String activateUser(ActivateUser activateUser) {
        return null;
    }
}
