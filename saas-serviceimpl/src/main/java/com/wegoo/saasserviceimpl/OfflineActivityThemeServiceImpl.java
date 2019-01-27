package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.OfflineActivityTheme;
import com.wegoo.saasdao.mapper.OfflineActivityThemeMapper;
import com.wegoo.saasservice.OfflineActivityThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityThemeServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2911:30
 */
@Service
public class OfflineActivityThemeServiceImpl implements OfflineActivityThemeService {
    @Autowired
    private OfflineActivityThemeMapper offlineActivityThemeMapper;

    @Override
    public List<OfflineActivityTheme> getOfflineActivityThemeListByQueryType(String merchantId, String queryType) {
        return offlineActivityThemeMapper.getOfflineActivityThemeListByQueryType(merchantId, queryType);
    }

    @Override
    public OfflineActivityTheme getOfflineActivityThemeDetailByThemeId(String merchantId, Integer themeId) {
        return offlineActivityThemeMapper.getOfflineActivityThemeDetailByThemeId(merchantId, themeId);
    }

    @Override
    public void updateBuyCountById(Integer activityThemeId) {
        int count = offlineActivityThemeMapper.updateBuyCountById(activityThemeId);
        if (count == 0) {
            BSUtil.isTrue(false, "更新主题参加人数失败");
        }

    }

}
