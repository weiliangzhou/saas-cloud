package com.wegoo.saas.member.FB;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wegoo.baseresult.BaseResult;
import com.wegoo.model.vo.SignInVo;
import com.wegoo.saas.member.feginService.OfflineActivityFeginService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class OfflineActivityFB implements OfflineActivityFeginService {
    @Override
    public String getOfflineActivityThemeList(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getOfflineActivityThemeDetailByThemeId(@RequestBody JSONObject jsonObject) {
        return JSON.toJSONString(new BaseResult("500", "大师兄，师傅被妖怪抓走了", null));
    }

    @Override
    public String getOfflineActivityListByThemeId(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getOfflineActivityChangeList(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getActivityOrderDetail(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String operatorSignIn(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String signIn(@RequestBody SignInVo signInVo) {
        return null;
    }

    @Override
    public String getActivityCodeDetail(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getActivityOrderList(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String change(@RequestBody JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getBindUserOrderCount(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String getUserOrderCount(JSONObject jsonObject) {
        return null;
    }

}
