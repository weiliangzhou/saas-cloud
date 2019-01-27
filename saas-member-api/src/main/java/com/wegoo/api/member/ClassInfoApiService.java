package com.wegoo.api.member;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClassInfoApiService {
    @PostMapping("/qudao-member/saas/classinfo/getClassInfoList")
    String getClassInfoList(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/classinfo/getClassInfoByClassInfoId")
    String getClassInfoByClassInfoId(@RequestBody JSONObject jsonObject);
}
