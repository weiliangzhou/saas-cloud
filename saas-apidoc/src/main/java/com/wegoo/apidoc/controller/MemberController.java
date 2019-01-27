package com.wegoo.apidoc.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.po.UserCertification;
import com.wegoo.model.po.UserInfo;
import com.wegoo.model.vo.OrderVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 二师兄超级帅
 * @Title: MemberController
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1718:14
 */
@Api2Doc(name = "用户管理")
@RestController
public class MemberController {

    @ApiComment("普通用户注册登录")
    @RequestMapping(name = "普通用户注册登录",
            value = "/qudao-member/saas/member/loginAndRegister", method = RequestMethod.POST)
    public BaseResult loginAndRegister(@ApiComment("手机号码") String phone,
                                       @ApiComment("验证码") String msgCode,
                                       @ApiComment("姓名") String realName,
                                       @ApiComment(value = "注册方式", sample = "0:网页 1:微信-H5 2:抖音 3:公众号 4:今日头条 5:业务员") Integer registerFrom,
                                       @ApiComment(value = "关联第三方", sample = "0:网页 1:微信-H5 2: 微信-小程序 3:线下导入 4:ios 5 :android") Integer channelId,
                                       @ApiComment(value = "微信授权code") String wechatCode,
                                       @ApiComment(value = "商户号") String merchantId) {
        return new BaseResult();
    }

    @ApiComment("导入用户激活")
    @RequestMapping(name = "导入用户激活",
            value = "/qudao-member/saas/member/activateUser", method = RequestMethod.POST)
    public BaseResult activateUser(@ApiComment("手机号码") String phone,
                                   @ApiComment("验证码 发送验证码类型=4") String msgCode,
                                   @ApiComment(value = "微信授权code") String wechatCode,
                                   @ApiComment(value = "商户号") String merchantId) {
        return new BaseResult();
    }

    @ApiComment("普通用户登录")
    @RequestMapping(name = "普通用户登录",
            value = "/qudao-member/saas/member/login", method = RequestMethod.POST)
    public BaseResult login(@ApiComment("手机号码") String phone,
                            @ApiComment("验证码") String msgCode
    ) {
        return new BaseResult();
    }

    @ApiComment("微信用户登录")
    @RequestMapping(name = "微信用户登录",
            value = "/qudao-member/saas/member/wxLogin", method = RequestMethod.POST)
    public BaseResult wxLogin(@ApiComment("微信授权code") String wechatCode,
                              @ApiComment("商户号") String merchantId,
                              @ApiComment(value = "通道", sample = "0:网页 1:微信-H5 2: 微信-小程序 3:线下导入 4:ios 5 :android") Integer channelId
    ) {
        return new BaseResult();
    }


    @ApiComment("业务人员注册登录")
    @RequestMapping(name = "业务人员注册登录",
            value = "/qudao-member/saas/member/busLogin", method = RequestMethod.POST)
    public BaseResult busLogin(@ApiComment("手机号码") String phone,
                               @ApiComment("验证码") String msgCode,
                               @ApiComment(value = "注册方式", sample = " 0:H5 1:小程序 2:线下导入") Integer registerFrom,
                               @ApiComment(value = "关联第三方", sample = "0:H5 1:小程序 2:QQ") Integer channelId,
                               @ApiComment(value = "微信授权code") String wechatCode,
                               @ApiComment(value = "商户号") String merchantId) {
        return new BaseResult();
    }

    @ApiComment("绑定用户上级关系")
    @RequestMapping(name = "绑定用户上级关系",
            value = "/qudao-member/saas/member/auth/bindUserReferrer", method = RequestMethod.POST)
    public BaseResult bindUserReferrer(@ApiComment("用户编号") String userId,
                                       @ApiComment("上级用户编号") String referrer,
                                       @ApiComment("商户号") Integer merchantId
    ) {
        return new BaseResult();
    }

    @ApiComment("发送短信验证码")
    @RequestMapping(name = "发送短信验证码",
            value = "/qudao-member/saas/member/sendMsgCode", method = RequestMethod.POST)
    public BaseResult sendMsgCode(@ApiComment("手机号码") String phone,
                                  @ApiComment(value = "业务类型", sample = "1:注册登录  4:激活用户导入") String busCode) {
        return new BaseResult();
    }

    @ApiComment("实名认证")
    @RequestMapping(name = "实名认证",
            value = "/qudao-member/saas/member/auth/certification", method = RequestMethod.POST)
    public BaseResult certification(@ApiComment("手机号码") String phone,
                                    @ApiComment(value = "姓名", sample = "张三") String realName,
                                    @ApiComment(value = "城市", sample = "浙江杭州") String city,
                                    @ApiComment(value = "性别", sample = "0：男 1：女") String sex,
                                    @ApiComment(value = "职业", sample = "品牌商") String profession,
                                    @ApiComment(value = "验证码", sample = "1344") String code,
                                    @ApiComment(value = "身份证号", sample = "321654894986854681") String idCardNnm
    ) {
        return new BaseResult();
    }

    @ApiComment("获取实名认证信息")
    @RequestMapping(name = "获取实名认证信息",
            value = "/qudao-member/saas/member/auth/getCertificationInfoByUserId", method = RequestMethod.POST)
    public UserCertification getCertificationInfoByUserId(@ApiComment("微信号") String userId
    ) {
        return new UserCertification();
    }

    @ApiComment("用户绑定微信号")
    @RequestMapping(name = "用户绑定微信号",
            value = "/qudao-member/saas/member/auth/bindUserInfoWechatNo", method = RequestMethod.POST)
    public BaseResult bindUserInfoWechatNo(@ApiComment("微信号") String weChatNo
    ) {
        return new BaseResult();
    }


    @ApiComment("微信网页JS执行权限")
    @RequestMapping(name = "我的-用户信息 微信网页JS执行权限",
            value = "/qudao-member/saas/member/getGzhJsApiToken", method = RequestMethod.POST)
    public BaseResult getGzhJsApiToken(@ApiComment("商户号") String merchantId,
                                       @ApiComment("地址") String url
    ) {
        return new BaseResult();
    }

    @ApiComment("我的-用户信息")
    @RequestMapping(name = "我的-用户信息",
            value = "/qudao-member/saas/member/auth/getAllUserInfo", method = RequestMethod.POST)
    public UserInfo getAllUserInfo(@ApiComment("商户号") String merchantId
    ) {
        return new UserInfo();
    }

    @ApiComment("我的业务员")
    @RequestMapping(name = "我的业务员",
            value = "/qudao-member/saas/member/auth/getUserReferrerInfo", method = RequestMethod.POST)
    public UserInfo getUserReferrerInfo(@ApiComment("商户号") String merchantId
    ) {
        return new UserInfo();
    }

    @ApiComment("某某某邀请你")
    @RequestMapping(name = "某某某邀请你",
            value = "/qudao-member/saas/member/getUserInfoByUserId", method = RequestMethod.POST)
    public UserInfo getUserInfoByUserIdWithoutToken(@ApiComment("userId") String userId, @ApiComment("商户号") String merchantId
    ) {
        return new UserInfo();
    }

    /**
     * 获取用户分享图片
     */
    @ApiComment("获取推广二维码")
    @RequestMapping(name = "获取推广二维码",
            value = "/qudao-member/saas/member/auth/getH5QrCode", method = RequestMethod.POST)
    public BaseResult getH5QrCode(@ApiComment("url") String url, @ApiComment("themeId") String themeId, @ApiComment("merchantId") String merchantId) {
        return new BaseResult();
    }

    /**
     * 获取我的推广码
     */
    @ApiComment("获取我的推广码")
    @RequestMapping(name = "获取我的推广码",
            value = "/qudao-member/saas/member/auth/getMyH5QrCode", method = RequestMethod.POST)
    public BaseResult getMyH5QrCode(@ApiComment("url") String url, @ApiComment("merchantId") String merchantId) {
        return new BaseResult();
    }

    @ApiComment("获取已购课程列表")
    @RequestMapping(name = "获取已购课程列表",
            value = "/qudao-member/saas/member/auth/getOrderList", method = RequestMethod.POST)
    public OrderVo getOrderList(@ApiComment("商户号") String merchantId,
                                @ApiComment("pageNum") Integer pageNum,
                                @ApiComment("pageSize") Integer pageSize,
                                @ApiComment("手机号") String phone,
                                @ApiComment("开始时间") Date activityStartTime,
                                @ApiComment("结束时间") Date activityEndTime,
                                @ApiComment(value = "真实姓名", sample = "1344") String realName,
                                @ApiComment("订单状态 0未付款 1已发货 2已完成 -1超时关闭") Integer orderStatus,
                                @ApiComment("我邀请的人的userId") String userId,
                                @ApiComment(value = "1:查询业绩订单；2：查询我邀请的人的订单；3：查询我的订单", sample = "1") Integer type) {
        OrderVo orderVo = new OrderVo();
        return orderVo;
    }

    @ApiComment("用户购买完善用户信息")
    @RequestMapping(name = "用户购买完善用户信息",
            value = "/qudao-member/saas/member/auth/perfectUserInfo", method = RequestMethod.POST)
    public BaseResult perfectUserInfo(@ApiComment("身份证号") String idCardNum,
                                      @ApiComment("真实姓名") String realName,
                                      @ApiComment("订单号") String orderNo,
                                      @ApiComment("手机号") String phone,
                                      @ApiComment("活动id") Integer activityId,
                                      @ApiComment("验证码") String msgCode,
                                      @ApiComment("验证标记位 0需要验证手机号 1不需要") Integer validateFlag
    ) {
        return new BaseResult();
    }


    @ApiComment("获取用户信息")
    @RequestMapping(name = "获取用户信息",
            value = "/qudao-member/saas/member/auth/getUserInfoByUserId", method = RequestMethod.POST)
    public UserInfo getUserInfoByUserId(@ApiComment("userId") String userId
    ) {
        return new UserInfo();
    }

    @ApiComment("修改咨询信息")
    @RequestMapping(name = "修改咨询信息",
            value = "/qudao-member/saas/member/auth/changeConsultInfo", method = RequestMethod.POST)
    public BaseResult changeConsultInfo(@ApiComment("咨询手机号") String consultPhone,
                                        @ApiComment("咨询微信号") String consultWechatNo,
                                        @ApiComment("0展示注册手机号 1展示咨询手机号 2展示咨询微信号") Integer consultShowType
    ) {
        return new BaseResult();
    }

    @ApiComment("获取咨询信息")
    @RequestMapping(name = "获取咨询信息",
            value = "/qudao-member/saas/member/auth/getConsultInfo", method = RequestMethod.POST)
    public UserInfo getConsultInfo(
    ) {
        return new UserInfo();
    }


}
