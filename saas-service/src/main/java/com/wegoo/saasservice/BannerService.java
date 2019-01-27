package com.wegoo.saasservice;

import com.wegoo.model.po.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> getBannerList(Banner banner);
}
