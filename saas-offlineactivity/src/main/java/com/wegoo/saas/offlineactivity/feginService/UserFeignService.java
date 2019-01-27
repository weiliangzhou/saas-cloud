package com.wegoo.saas.offlineactivity.feginService;

import com.wegoo.api.member.MemberApiService;
import com.wegoo.model.vo.wechat.UserInfoVo;
import com.wegoo.saas.offlineactivity.FB.UserFB;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by 二师兄超级帅 on 2018/4/10.
 */
@FeignClient(value = "qudao-member", fallback = UserFB.class)
public interface UserFeignService extends MemberApiService {

}
