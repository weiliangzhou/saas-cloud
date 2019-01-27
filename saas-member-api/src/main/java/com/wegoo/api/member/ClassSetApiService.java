package com.wegoo.api.member;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClassSetApiService {
    @PostMapping("/qudao-member/saas/classset/getClassSetList")
    String getClassSetList(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/classset/auth/getClassSetByClassSetId")
    String getClassSetByClassSetId(@RequestBody JSONObject jsonObject);
}
