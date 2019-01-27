package com.wegoo.saas.member.feginService;

import com.wegoo.api.offlineactivity.OfflineActivityApiService;
import com.wegoo.saas.member.FB.OfflineActivityFB;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "qudao-offlineactivity", fallback = OfflineActivityFB.class)
public interface OfflineActivityFeginService extends OfflineActivityApiService {

}
