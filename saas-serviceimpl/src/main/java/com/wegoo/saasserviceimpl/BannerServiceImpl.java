package com.wegoo.saasserviceimpl;

import com.wegoo.model.po.Banner;
import com.wegoo.saasdao.mapper.BannerMapper;
import com.wegoo.saasservice.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> getBannerList(Banner banner) {
        return bannerMapper.getBannerList(banner);
    }
}
