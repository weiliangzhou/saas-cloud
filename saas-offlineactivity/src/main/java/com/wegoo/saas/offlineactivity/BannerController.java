package com.wegoo.saas.offlineactivity;

import com.alibaba.fastjson.JSONObject;
import com.wegoo.baseservice.BaseService;
import com.wegoo.model.po.Banner;
import com.wegoo.saasservice.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄
 */
@RestController
@RequestMapping("/qudao-offlineactivity/saas")
public class BannerController extends BaseService {
    @Autowired
    private BannerService bannerService;

    @PostMapping("/getBannerList")
    @Cacheable(cacheNames = "getBannerList", keyGenerator = "zwlKey")
    public String getBannerList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer portType = jsonObject.getInteger("portType");
        Banner banner = new Banner();
        banner.setMerchantId(merchantId);
        if (null != portType) {
            banner.setPortType(portType);
        } else {
            banner.setPortType(0);
        }
        List<Banner> bannerList = bannerService.getBannerList(banner);
        return setSuccessResult(bannerList);
    }
}
