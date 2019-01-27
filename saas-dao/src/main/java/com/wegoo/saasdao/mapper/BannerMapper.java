package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.Banner;

import java.util.List;

public interface BannerMapper {
    List<Banner> getBannerList(Banner banner);
}