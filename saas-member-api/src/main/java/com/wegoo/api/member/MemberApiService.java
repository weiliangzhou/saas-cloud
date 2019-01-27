package com.wegoo.api.member;

import com.alibaba.fastjson.JSONObject;
import com.wegoo.model.po.Certification;
import com.wegoo.model.vo.ActivateUser;
import com.wegoo.model.vo.LoginParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by 二师兄超级帅 on 2018/10/18.
 */
public interface MemberApiService {
    @PostMapping("/qudao-member/saas/member/auth/certification")
    String certification(@RequestBody Certification certification);

    @PostMapping("/qudao-member/saas/member/loginAndRegister")
    String loginAndRegister(@RequestBody LoginParams loginParams);

    @PostMapping("/qudao-member/saas/member/login")
    String login(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/busLogin")
    String busLogin(@RequestBody LoginParams loginParams);

    @PostMapping("/qudao-member/saas/member/sendMsgCode")
    String sendRegisterCode(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/bindUserReferrer")
    String bindUserReferrer(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/getUserOpenIdByUserIdAndMerchantIdAndChannel")
    String getUserOpenIdByUserIdAndMerchantIdAndChannel(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/auth/getUserMemberByUserIdAndMerchantId")
    String getUserMemberByUserIdAndMerchatId(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/auth/getUserInfoByUserId")
    String getUserInfoByUserId();

    @PostMapping("/qudao-member/saas/member/auth/getAllUserInfo")
    String getAllUserInfo(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/auth/getCertificationInfoByUserId")
    String getCertificationInfoByUserId(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/getMyUserList")
    String getMyUserList(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/auth/getReferrerInfoByUserId")
    String getReferrerInfoByUserId(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/auth/getGzhUserInfo")
    String getGzhUserInfo(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/getUserMemberByUserIdAndMerchantId")
    String getUserMemberByUserIdAndMerchantIdWithoutToken(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/getUserInfoByUserId")
    String getUserInfoByUserIdWithoutToken(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/auth/getOrderList")
    String getOrderList(@RequestBody JSONObject jsonObject);

    @PostMapping("/qudao-member/saas/member/activateUser")
    String activateUser(@RequestBody ActivateUser activateUser);
}
