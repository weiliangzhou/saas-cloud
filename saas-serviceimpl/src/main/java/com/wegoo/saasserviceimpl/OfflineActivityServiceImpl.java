package com.wegoo.saasserviceimpl;

import com.wegoo.exception.BSUtil;
import com.wegoo.model.po.OfflineActivity;
import com.wegoo.model.po.OfflineActivityOrder;
import com.wegoo.model.po.OfflineActivityTheme;
import com.wegoo.saasdao.mapper.OfflineActivityMapper;
import com.wegoo.saasservice.OfflineActivityCodeService;
import com.wegoo.saasservice.OfflineActivityService;
import com.wegoo.saasservice.OfflineActivityThemeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wegoo.model.BigDecimalUtil.BigDecimalUtil.div;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2713:44
 */
@Service
public class OfflineActivityServiceImpl implements OfflineActivityService {
    @Autowired
    private OfflineActivityMapper offlineActivityMapper;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;

    @Override
    public OfflineActivity getOneByActivityId(Integer activityId) {
        return offlineActivityMapper.selectByPrimaryKey(activityId);
    }

    @Override
    public OfflineActivity getOneByActivityIdAndCheckTime(Integer activityId) {
        return offlineActivityMapper.getOneByActivityIdAndCheckTime(activityId);
    }

    @Override
    public List<OfflineActivity> getOfflineActivityListByThemeId(String merchantId, Integer activityThemeId, String userId) {
        List<OfflineActivity> offlineActivityList = offlineActivityMapper.getOfflineActivityListByThemeId(merchantId,activityThemeId);
        Integer count = offlineActivityCodeService.getAlreadyBuyCountByUserIdAndThemeId(userId,activityThemeId,merchantId);
        if(0==count){
            for(OfflineActivity offlineActivity:offlineActivityList){
                offlineActivity.setActivityPriceDesc(div(100,offlineActivity.getActivityPrice(),2)+"");
                OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId,offlineActivity.getActivityThemeId());
                offlineActivity.setImgUrl(offlineActivityTheme.getImgUrl());
                offlineActivity.setThemeName(offlineActivityTheme.getThemeName());
                offlineActivity.setActivityAddressDesc(offlineActivity.getActivityAddress());
            }
        }else{
            for(OfflineActivity offlineActivity:offlineActivityList){
                OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId,offlineActivity.getActivityThemeId());
                if(1 == offlineActivity.getIsRetraining()){
                    offlineActivity.setActivityPriceDesc(div(100,offlineActivity.getRetrainingPrice(),2)+"");
                    offlineActivity.setImgUrl(offlineActivityTheme.getImgUrl());
                    offlineActivity.setThemeName(offlineActivityTheme.getThemeName());
                    offlineActivity.setActivityAddressDesc(offlineActivity.getActivityAddress()+"-复训");
                }else {
                    offlineActivity.setActivityPriceDesc(div(100,offlineActivity.getActivityPrice(),2)+"");
                    offlineActivity.setImgUrl(offlineActivityTheme.getImgUrl());
                    offlineActivity.setThemeName(offlineActivityTheme.getThemeName());
                    offlineActivity.setActivityAddressDesc(offlineActivity.getActivityAddress());
                }
            }
        }
        return offlineActivityList;
    }

    @Override
    public void updateBuyCountById(Integer activityId) {
        int count= offlineActivityMapper.updateBuyCountById(activityId);
        if (count == 0) {
            BSUtil.isTrue(false,"更新活动报名人数失败");
        }
    }

    @Override
    public void updateOldBuyCountById(Integer activityId) {
        int count= offlineActivityMapper.updateOldBuyCountById(activityId);
        if (count == 0) {
            BSUtil.isTrue(false,"更新活动报名人数失败");
        }
    }

    @Override
    public List<OfflineActivity> getOfflineActivityChangeList(String merchantId, Integer activityThemeId, Integer activityId) {
        Date date = new Date();
        List<OfflineActivity> offlineActivityList = offlineActivityMapper.getOfflineActivityChangeListByThemeId(merchantId,activityThemeId);
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId, activityThemeId);
        String themeName = null;
        String imgUrl = null;
        if(null != offlineActivityTheme){
            themeName = offlineActivityTheme.getThemeName();
            imgUrl = offlineActivityTheme.getImgUrl();
        }

        List<OfflineActivity> offlineActivityThemeVoList = new ArrayList<OfflineActivity>();
        for(OfflineActivity offlineActivity : offlineActivityList){
            Date startTime = offlineActivity.getActivityStartTime();
            //OfflineActivityVo offlineActivityChange = new OfflineActivityVo();
            if((startTime.getTime()-date.getTime())/(24*3600*1000) >= 7 ){
                //offlineActivityChange.setOfflineActivity(offlineActivity);

                /*if(offlineActivity.getId().equals(activityId)){
                    offlineActivityChange.setFlag(1);
                }else{
                    offlineActivityChange.setFlag(0);
                }*/
                offlineActivity.setThemeName(themeName);
                offlineActivity.setImgUrl(imgUrl);
                offlineActivityThemeVoList.add(offlineActivity);
            }

        }
        return offlineActivityThemeVoList;
    }

    @Override
    public int getStartTime(int activityId) {
        return offlineActivityMapper.getStartTime(activityId);
    }

    @Override
    public List<Integer> getChangeActivityIdList() {
        return offlineActivityMapper.getChangeActivityIdList();
    }



}
