package com.wegoo.api.offlineactivity;

import com.alibaba.fastjson.JSONObject;
import com.wegoo.model.vo.SignInVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by 二师兄超级帅 on 2018/4/10.
 */
public interface OfflineActivityApiService {

    @PostMapping("/saas/offlineActivity/getOfflineActivityThemeList")
    String getOfflineActivityThemeList(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/getOfflineActivityThemeDetailByThemeId")
    String getOfflineActivityThemeDetailByThemeId(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/getOfflineActivityListByThemeId")
    String getOfflineActivityListByThemeId(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/auth/getOfflineActivityChangeList")
    String getOfflineActivityChangeList(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/auth/getOfflineActivityChangeList")
    String getActivityOrderDetail(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/offlineLogin")
    String operatorSignIn(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/signIn")
    String signIn(@RequestBody SignInVo signInVo);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/getActivityCodeDetail")
    String getActivityCodeDetail(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/auth/getActivityOrderList")
    String getActivityOrderList(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/change")
    String change(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/auth/getBindUserOrderCount")
    public String getBindUserOrderCount(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-offlineactivity/saas/offlineActivity/auth/getUserOrderCount")
    public String getUserOrderCount(@RequestBody JSONObject jsonObject);

}
