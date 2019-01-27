package com.wegoo.apidoc.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.po.Banner;
import com.wegoo.model.po.OfflineActivity;
import com.wegoo.model.po.OfflineActivityTheme;
import com.wegoo.model.vo.ActivityCodeDetail;
import com.wegoo.model.vo.OfflineActivityOrderVo;
import com.wegoo.model.vo.OfflineActivityThemeVo;
import com.wegoo.model.vo.WxPayVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api2Doc(name = "线下活动")
@RestController
public class OfflineActivityController {
    @RequestMapping(name = "线下活动主题列表",
            value = "/qudao-offlineactivity/saas/offlineActivity/getOfflineActivityThemeList", method = RequestMethod.POST)
    public OfflineActivityTheme getOfflineActivityThemeList(@ApiComment("merchantId") String merchantId,
                                                            @ApiComment("queryType  0查询推荐列表 其他查询全部") String queryType,
                                                            @ApiComment("pageSize") String pageSize,
                                                            @ApiComment("pageNum") String pageNum
    ) {
        OfflineActivityTheme offlineActivityTheme = new OfflineActivityTheme();
        return offlineActivityTheme;
    }

    @RequestMapping(name = "线下活动主题详情介绍页",
            value = "/qudao-offlineactivity/saas/offlineActivity/getOfflineActivityThemeDetailByThemeId", method = RequestMethod.POST)
    public OfflineActivityTheme getOfflineActivityThemeDetailByThemeId(@ApiComment("merchantId") String merchantId,
                                                                       @ApiComment("主题id") String themeId
    ) {
        OfflineActivityTheme offlineActivityTheme = new OfflineActivityTheme();
        return offlineActivityTheme;
    }

    @RequestMapping(name = "线下活动主题详情购买",
            value = "/qudao-offlineactivity/saas/offlineActivity/auth/getOfflineActivityListByThemeId", method = RequestMethod.POST)
    public OfflineActivityThemeVo getOfflineActivityListByThemeId(@ApiComment("merchantId") String merchantId,
                                                                  @ApiComment("主题id") Integer activityThemeId
    ) {
        OfflineActivityThemeVo offlineActivity = new OfflineActivityThemeVo();
        return offlineActivity;
    }

    @RequestMapping(name = "获取可改签课程列表",
            value = "/qudao-offlineactivity/saas/offlineActivity/auth/getOfflineActivityChangeList", method = RequestMethod.POST)
    public OfflineActivity getChangeList(@ApiComment("merchantId") String merchantId,
                                         @ApiComment("主题id") Integer activityThemeId) {
        OfflineActivity offlineActivity = new OfflineActivity();
        return offlineActivity;
    }

    @RequestMapping(name = "获取订单详情",
            value = "/qudao-offlineactivity/saas/offlineActivity/auth/getActivityOrderDetail", method = RequestMethod.POST)
    public OfflineActivityOrderVo getActivityOrderDetail(@ApiComment("orderNo") String orderNo) {
        OfflineActivityOrderVo offlineActivityOrderVo = new OfflineActivityOrderVo();
        return offlineActivityOrderVo;
    }

    @RequestMapping(name = "操作员签到",
            value = "/qudao-offlineactivity/saas/offlineActivity/offlineLogin", method = RequestMethod.POST)
    public BaseResult operatorSignIn(@ApiComment("操作员") String operator, @ApiComment("密码") Integer password, @ApiComment("商户号") String merchantId) {
        return new BaseResult();
    }

    @RequestMapping(name = "线下活动签到",
            value = "/qudao-offlineactivity/saas/offlineActivity/signIn", method = RequestMethod.POST)
    public BaseResult signIn(@ApiComment("activityCode") Integer activityCode,
                             @ApiComment("操作员") String operator, @ApiComment("商户号") String merchantId) {
        return new BaseResult();
    }

    @RequestMapping(name = "线下签到详情页",
            value = "/qudao-offlineactivity/saas/offlineActivity/getActivityCodeDetail", method = RequestMethod.POST)
    public ActivityCodeDetail getActivityCodeDetail(@ApiComment("activityCode") String activityCode
    ) {
        ActivityCodeDetail activityCodeDetail = new ActivityCodeDetail();
        return activityCodeDetail;
    }

    @RequestMapping(name = "线下报名",
            value = "/qudao-offlineactivity/saas/offlineActivity/auth/buy", method = RequestMethod.POST)
    public WxPayVo offlineActivityBuy(
            @ApiComment("activityId") Integer activityId,
            @ApiComment("商户号") String merchantId

    ) {
        WxPayVo wxPayVo = new WxPayVo();
        return wxPayVo;
    }

    @ApiComment("改签")
    @RequestMapping(name = "改签",
            value = "/qudao-offlineactivity/saas/offlineActivity/auth/change", method = RequestMethod.POST)
    public BaseResult change(
            @ApiComment(value = "线下活动id", sample = "1344") int activityId,
            @ApiComment(value = "订单号", sample = "32165489498") String orderNo
    ) {
        return new BaseResult();
    }

    @ApiComment("获取订单列表")
    @RequestMapping(name = "获取订单列表",
            value = "/qudao-offlineactivity/saas/offlineActivity/auth/getActivityOrderList", method = RequestMethod.POST)
    public OfflineActivityOrderVo getActivityOrderList(
            @ApiComment(value = "商户号", sample = "1507477551") String merchantId,
            @ApiComment(value = "userId", sample = "1") String userId,
            @ApiComment(value = "不传值为业绩订单，传值为个人订单", sample = "123") String referrer,
            @ApiComment(value = "主题Id", sample = "1507477551") int activityThemeId,
            @ApiComment(value = "真实姓名", sample = "1344") String realName,
            @ApiComment(value = "pageSize", sample = "1") int pageSize,
            @ApiComment(value = "pageNum", sample = "2") int pageNum,
            @ApiComment(value = "是否可用", sample = "1") int isUsed,
            @ApiComment(value = "1:查询业绩订单；2：查询我要i清的人的订单；3：查询我的订单", sample = "1") int type,
            @ApiComment(value = "手机号", sample = "32165489498") String phone
    ) {
        return new OfflineActivityOrderVo();
    }

    @ApiComment("我邀请的人")
    @RequestMapping(name = "我邀请的人",
            value = "/qudao-offlineactivity//saas/offlineActivity/auth/getMyUserList", method = RequestMethod.POST)
    public BaseResult getMyUserList(
            @ApiComment(value = "真实姓名", sample = "张三") String realName,
            @ApiComment(value = "日期类型", sample = "0") int dateType,
            @ApiComment(value = "pageSize", sample = "1") int pageSize,
            @ApiComment(value = "pageNum", sample = "2") int pageNum,
            @ApiComment(value = "手机号", sample = "32165489498") String phone
    ) {
        return new BaseResult();
    }

    @ApiComment(value = "获取banner列表", seeClass = Banner.class)
    @RequestMapping(name = "获取banner列表", value = "/qudao-offlineactivity/saas/getBannerList", method = RequestMethod.POST)
    public Banner getBannerList(@ApiComment("商户号") String merchantId, @ApiComment("端口类型 0、小程序 1、H5") Integer portType) {
        Banner banner = new Banner();
        return banner;
    }
}
