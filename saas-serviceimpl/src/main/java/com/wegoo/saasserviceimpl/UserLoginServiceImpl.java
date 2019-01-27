package com.wegoo.saasserviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wegoo.config.PayNotifyProperties;
import com.wegoo.constants.Available;
import com.wegoo.constants.WxConstans;
import com.wegoo.constants.msg.MsgCode;
import com.wegoo.constants.user.Channel;
import com.wegoo.constants.user.RegisterSource;
import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.exception.BSUtil;
import com.wegoo.exception.BusinessException;
import com.wegoo.exception.WxCodeVerfiyException;
import com.wegoo.model.po.*;
import com.wegoo.model.vo.LoginParams;
import com.wegoo.model.vo.wechat.UserInfoVo;
import com.wegoo.model.vo.wechat.WebAccessTokenVo;
import com.wegoo.saasdao.mapper.ActivateInfoMapper;
import com.wegoo.saasservice.*;
import com.wegoo.saasservice.wechat.WechatService;
import com.wegoo.tokenManager.TokenManager;
import com.wegoo.tokenManager.TokenModel;
import com.wegoo.utils.EmojiFilter;
import com.wegoo.utils.HttpsUtils;
import com.wegoo.utils.RedisUtil;
import com.wegoo.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 二师兄
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMemberService userMemberService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserOpenIdService userOpenIdService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MsgSenderService msgSenderService;
    @Autowired
    private UserReferrerService userReferrerService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private PayNotifyProperties payNotifyProperties;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private ActivateInfoMapper activateInfoMapper;

    @Override
    @Transactional
    public String login(LoginParams loginParams) {
        verify(loginParams);
        String merchantId = loginParams.getMerchantId();
        String wechatCode = loginParams.getWechatCode();
        //通过code，merchantId,channelId获取openId,再通过openId查saas_user_openId表查userId
        WebAccessTokenVo gzhWebAccessToken = wechatService.getGzhWebAccessToken(wechatCode, merchantId);
        String openid = gzhWebAccessToken.getOpenid();
        Channel channel = Channel.getChannelById(loginParams.getChannelId());
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByOpenIdAndMerchantIdAndChannel(openid, merchantId, channel);
        String userId;
        if (null == userOpenId) {
            //注册用户
            User user = registerUser(null, loginParams.getRegisterFrom());
            userId = user.getUserId();
            //设置账号关联商户等级
            addUserMember(userId, loginParams.getMerchantId(), loginParams.getMemberLevel());
            //获取用户基本信息 添加第三关联
            String openId = addUserInfo(userId, merchantId, gzhWebAccessToken, loginParams.getRegisterFrom(), null, null);
            //添加第三方关联帐号
            addUserOpenId(userId, loginParams.getMerchantId(), loginParams.getChannelId(), openId);
            //绑定推荐人
            //fixme 业务员考虑
            if (loginParams.getReferrer() != null) {
                userReferrerService.bindUserRef(userId, loginParams.getReferrer(), merchantId);
            }

        } else {
            userId = userOpenId.getUserId();
            //fixme 需要根据code 获取昵称 并更新到 saas_user_info 中的nick_name
            UserInfoVo gzhUserInfo = wechatService.getGzhUserInfo(merchantId, userId);
            if (gzhUserInfo != null) {
                String nickName = gzhUserInfo.getNickname();
                String logoUrl = gzhUserInfo.getHeadimgurl();
                if (StringUtils.isNotBlank(nickName) || StringUtils.isNotBlank(logoUrl)) {
                    userInfoService.updateNickNameAndLogoByUserId(EmojiFilter.filterEmoji(nickName), logoUrl, userId);
                    //删除 海报 redisKey
                    RedisUtil.del(userId + "s_shareimg_*");
                }
            }
        }
        //返回用户登录态
        TokenModel model = tokenManager.createToken(userId);
        String token = model.getSignToken();
        return token;
    }

    @Override
    @Transactional
    public String busLogin(LoginParams loginParams) {
        verifyBus(loginParams);
        String merchantId = loginParams.getMerchantId();
        String wechatCode = loginParams.getWechatCode();
        String phone = loginParams.getPhone();
        String realName = loginParams.getRealName();
        //通过code，merchantId,channelId获取openId,再通过openId查saas_user_openId表查userId
        WebAccessTokenVo gzhWebAccessToken = wechatService.getGzhWebAccessToken(wechatCode, merchantId);
        String openid = gzhWebAccessToken.getOpenid();
        Channel channel = Channel.getChannelById(loginParams.getChannelId());
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByOpenIdAndMerchantIdAndChannel(openid, merchantId, channel);
        String userId;
        if (null == userOpenId) {
            User user = new User();
            //注册用户
            try {
                user = registerUser(phone, loginParams.getRegisterFrom());
            } catch (Exception e) {
                throw new BusinessException("该手机号已经被其他微信注册了！");
            }
            userId = user.getUserId();
            //设置账号关联商户等级
            addUserMember(userId, loginParams.getMerchantId(), loginParams.getMemberLevel());
            //获取用户基本信息 添加第三关联
            String openId = addUserInfo(userId, merchantId, gzhWebAccessToken, loginParams.getRegisterFrom(), phone, realName);
            //添加第三方关联帐号
            addUserOpenId(userId, loginParams.getMerchantId(), loginParams.getChannelId(), openId);
        } else {
            userId = userOpenId.getUserId();
            //存在当前用户 则更新 saas_user saas_user_info saas_user_member
            //需要判断手机号码是否跟现在的一致，不一致则报错  该手机号已经被其他微信注册了！
//            User user = userService.getUserByUserId(userId);
//            String registerMobile = user.getRegisterMobile();
//            if (registerMobile.equals(phone)) {
            userService.updateRegisterMobileByUserId(phone, userId);
            userInfoService.updateRegisterMobileAndRealNameByUserId(phone, realName, userId);
            userMemberService.updateMemberLevel(userId, merchantId, 99);
            //清空推荐人 saas_user_referrer
            userReferrerService.removeReferrer(userId);
//            } else {
//                throw new BusinessException("该微信已经被其他手机号注册了！");
//            }
            //fixme 需要根据code 获取昵称 并更新到 saas_user_info 中的nick_name
            UserInfoVo gzhUserInfo = wechatService.getGzhUserInfo(merchantId, userId);
            if (gzhUserInfo != null) {
                String nickName = gzhUserInfo.getNickname();
                String logoUrl = gzhUserInfo.getHeadimgurl();
                if (StringUtils.isNotBlank(nickName) || StringUtils.isNotBlank(logoUrl)) {
                    //fixme 判断是否跟原先的值是否相等
                    userInfoService.updateNickNameAndLogoByUserId(EmojiFilter.filterEmoji(nickName), logoUrl, userId);
                    //删除 海报 redisKey
                    RedisUtil.del(userId + "s_shareimg_*");
                }
            }

        }
        //返回用户登录态
        TokenModel model = tokenManager.createToken(userId);
        String token = model.getSignToken();
        return token;
    }


    @Override
    public TokenModel wxLogin(String wechatCode, String merchantId, Channel channel) {
        if (StringUtils.isBlank(wechatCode)) {
            throw new BusinessException("微信code不能为空");
        }
        if (StringUtils.isBlank((merchantId))) {
            throw new BusinessException("微信商户号不能为空");
        }
        if (null == channel) {
            throw new BusinessException("登录通道不能为空");
        }
        WebAccessTokenVo webAccessTokenVo = wechatService.getGzhWebAccessToken(wechatCode, merchantId);

        if (null == webAccessTokenVo) {
            throw new BusinessException("登录出错");
        }
        if (webAccessTokenVo.getErrcode() != null && webAccessTokenVo.getErrcode().equals(40163)) {
            throw new WxCodeVerfiyException("微信code无效");
        }
        //缓存微信OPENID
        String accessTokenKey = String.format("wx_atk_%s", wechatCode);
        stringRedisTemplate.delete(accessTokenKey);
        stringRedisTemplate.boundValueOps(accessTokenKey).set(JSONObject.toJSONString(webAccessTokenVo), 5, TimeUnit.MINUTES);
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByOpenIdAndMerchantIdAndChannel(webAccessTokenVo.getOpenid(), merchantId, channel);
        if (userOpenId == null) {
            return null;
        } else {
            //fixme 需要根据code 获取昵称 并更新到 saas_user_info 中的nick_name
            UserInfoVo gzhUserInfo = wechatService.getGzhUserInfo(merchantId, null);
            String userId = CurrentUserInfoContext.getUserID();
            String nickName = gzhUserInfo.getNickname();
            String logoUrl = gzhUserInfo.getHeadimgurl();
            userInfoService.updateNickNameAndLogoByUserId(EmojiFilter.filterEmoji(nickName), logoUrl, userId);
            //删除 海报 redisKey
            RedisUtil.del(userId + "s_shareimg_*");
        }

        TokenModel model = tokenManager.createToken(userOpenId.getUserId());
        return model;
    }

    @Override
    public String activateUser(String merchantId, String wechatCode, String phone, Integer channelId, Integer registerFrom) {
        //通过wechatCode 插入是否存在用户，如果不存在 则注册用户
        //通过code，merchantId,channelId获取openId,再通过openId查saas_user_openId表查userId
        WebAccessTokenVo gzhWebAccessToken = wechatService.getGzhWebAccessToken(wechatCode, merchantId);
        String openid = gzhWebAccessToken.getOpenid();
        Channel channel = Channel.getChannelById(channelId);
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByOpenIdAndMerchantIdAndChannel(openid, merchantId, channel);
        String userId;
        if (null == userOpenId) {
            //注册用户
            User user = registerUser(phone, registerFrom);
            userId = user.getUserId();
            //设置账号关联商户等级
            addUserMember(userId, merchantId, 2);
            //获取用户基本信息 添加第三关联
            String openId = addUserInfo(userId, merchantId, gzhWebAccessToken, registerFrom, phone, null);
            //添加第三方关联帐号
            addUserOpenId(userId, merchantId, channelId, openId);
        } else {
            userId = userOpenId.getUserId();
            log.info(userId);
        }
        //根据手机号查询 是否 需要导入数据
        ActivateInfo activateInfo = activateInfoMapper.getDetailByPhone(phone);
        if (activateInfo == null) {
            BSUtil.isTrue(false, "请使用报名时提交的手机号");
        }
        if (1 == activateInfo.getIsUsed()) {
            BSUtil.isTrue(false, "已经激活");
        }
        log.info(JSON.toJSONString(activateInfo));
        Integer themeId = activateInfo.getThemeId();
        Integer activityId = activateInfo.getActivityId();
        String activityCode = activateInfo.getActivityCode();
        String idCardNum = activateInfo.getIdCardNum();
        Integer price = activateInfo.getThemePrice();
        String referrer = activateInfo.getReferrer();
        String referrerName = activateInfo.getReferrerName();
        String referrerPhone = activateInfo.getReferrerPhone();
        String qrCodeUrl = activateInfo.getQrCodeUrl();
        String realName = activateInfo.getRealName();
        User userLong = userService.getUserByUserId(userId);
        OfflineActivity offlineActivity=offlineActivityService.getOneByActivityId(activityId);
        //如果存在  则执行 导入(themeId,referrer,activityId,qrCodeUrl,activityCode) 导入完成之后 更新导入状态为1已完成
        //插入saas_offline_activity_order
        SimpleDateFormat sdf_yMdHm = new SimpleDateFormat("yyyyMMddHHmm");
        String orderNo = sdf_yMdHm.format(new Date()) + merchantId + userLong.getId() + themeId;
        OfflineActivityOrder offlineActivityOrder = new OfflineActivityOrder();
        offlineActivityOrder.setOrderNo(orderNo);
        offlineActivityOrder.setActivityThemeId(themeId);
        //活动兑换码生成规则
        offlineActivityOrder.setActivityCode(activityCode);
        offlineActivityOrder.setUserId(userId);
        offlineActivityOrder.setCreateTime(new Date());
        offlineActivityOrder.setAvailable(1);
        offlineActivityOrder.setMerchantId(merchantId);
        offlineActivityOrder.setOrderStatus(1);
        offlineActivityOrder.setPhone(phone);
        //是否复训 0不是1是
        offlineActivityOrder.setIsRetraining(0);
        //现阶段没有返佣
        offlineActivityOrder.setIsMaid(0);
        offlineActivityOrder.setActualMoney(price);
        offlineActivityOrder.setActivityPrice(price);
        offlineActivityOrder.setReferrer(referrer);
        offlineActivityOrder.setChangeTimes(0);
        offlineActivityOrder.setRealName(realName);
        offlineActivityOrder.setReferrerName(referrerName);
        offlineActivityOrder.setReferrerPhone(referrerPhone);
        offlineActivityOrder.setIdCardNum(idCardNum);
        offlineActivityOrder.setActivityId(activityId);
        offlineActivityOrder.setStartTime(offlineActivity.getActivityStartTime());
        offlineActivityOrder.setEndTime(offlineActivity.getActivityEndTime());
        log.info("订单数据" + offlineActivityOrder);
        offlineActivityOrderService.insertActivateUser(offlineActivityOrder);
        //插入saas_offline_activity_code
        //生成二维码
        log.info("开始生成二维码" + orderNo);
        OfflineActivityCode offlineActivityCode = new OfflineActivityCode();
        offlineActivityCode.setActivityCode(activityCode);
        offlineActivityCode.setActivityId(activityId);
        offlineActivityCode.setActivityThemeId(themeId);
        offlineActivityCode.setAvailable(1);
        offlineActivityCode.setMerchantId(merchantId);
        offlineActivityCode.setUserId(userId);
        offlineActivityCode.setCreateTime(new Date());
        offlineActivityCode.setQrCodeUrl(qrCodeUrl);
        offlineActivityCode.setIsUsed(0);
        offlineActivityCodeService.insert(offlineActivityCode);
        log.info("二维码生成成功:" + qrCodeUrl);
        //更新活动购买人数
        offlineActivityService.updateBuyCountById(activityId);
        //更新活动主题购买人数
        offlineActivityThemeService.updateBuyCountById(themeId);
        //购买成功之后，更新saas_user_referrer is_buy=1绑定关系
        userReferrerService.updateIsBuyAndReferrerByUserIdAndMerchantId(referrer, userId, merchantId);
        //更新用户真实姓名
        userInfoService.updateRealNameByUserId(realName, userId);

        activateInfoMapper.updateIsUsedByPhone(phone);

        userService.updateRegisterMobileByUserId(phone, userId);
        userInfoService.updateRegisterMobileAndRealNameByUserId(phone, realName, userId);

        //返回用户登录态
        TokenModel model = tokenManager.createToken(userId);
        String token = model.getSignToken();
        return token;
    }

    /**
     * 验证手机号码是否已经绑定微信号
     */
    private void verfiyPhoneBindWx(String openId, String merchantId, Channel channel) {
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByOpenIdAndMerchantIdAndChannel(openId, merchantId, channel);
        if (null != userOpenId) {
            throw new BusinessException("该微信号已绑定其他手机号码请先解绑");
        }
    }


    /**
     * 添加第三方关联帐号
     *
     * @param userId
     * @param merchantId
     * @param channel
     * @param openId
     */
    private void addUserOpenId(String userId, String merchantId, Integer channel, String openId) {
        UserOpenId userOpenId = new UserOpenId();
        userOpenId.setUserId(userId);
        userOpenId.setMerchantId(merchantId);
        userOpenId.setChannelId(channel);
        userOpenId.setOpenId(openId);
        userOpenId.setCreateTime(new Date());
        userOpenId.setAvailable(1);
        userOpenIdService.addUserOpenId(userOpenId);
    }

    /**
     * 添加用户信息
     *
     * @param userId       用户标识
     * @param merchantId   商户号
     * @param registerFrom 注册来源
     * @return 第三方 账号标识（OpenId） 微信要获取用户头像需要先获取OPENID交换,这个方法已经查到了OPENID就直接返回  没有就为NULL
     */
    private String addUserInfo(String userId, String merchantId, WebAccessTokenVo webAccessTokenVo, Integer registerFrom, String phone, String realName) {
        RegisterSource registerSource = RegisterSource.getRegisterSourceById(registerFrom);
        if (registerSource == null) {
            BSUtil.isTrue(false, "无效的第三方");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (null == merchant || StringUtils.isBlank(merchant.getMerchantId())) {
            BSUtil.isTrue(false, "无效商户号:" + merchantId);
        }
        String openId = null;
        //用户基本信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        // 获取接口URL

        String accessToken = webAccessTokenVo.getAccess_token();
        openId = webAccessTokenVo.getOpenid();
        //获取微信登录信息后 请求获取用户信息
        String args = String.format("&access_token=%s&openid=%s", accessToken, openId);
        String resultStr = HttpsUtils.sendGet(WxConstans.GET_WECHAT_USERINFO + args, null);
        Map userInfoResult = JSONObject.parseObject(resultStr, Map.class);
        if (!(userInfoResult.get("errcode") == null)) {
            BSUtil.isTrue(false, String.format("获取微信用户的基本信息出错 errorCode:%s errorMsg:%s", userInfoResult.get("errcode"), userInfoResult.get("errmsg")));
        }
        String nickName = userInfoResult.get("nickname") == null ? "" : userInfoResult.get("nickname").toString();
        String logoUrl = userInfoResult.get("headimgurl") == null ? "" : userInfoResult.get("headimgurl").toString();
        userInfo.setNickName(EmojiFilter.filterEmoji(nickName));
        userInfo.setLogoUrl(logoUrl);
        userInfo.setCreateTime(new Date());
        userInfo.setConsultShowType(0);
        if (StringUtils.isNotBlank(realName)) {
            userInfo.setRealName(realName);
        }
        userInfo.setRealName(realName);
        if (StringUtils.isNotBlank(phone)) {
            userInfo.setRegisterMobile(phone);
        }
        userInfoService.addUserInfo(userInfo);
        return openId;
    }

    /**
     * 添加用户关联商户
     *
     * @param userId
     * @param merchantId
     * @param memberLevel
     */
    private void addUserMember(String userId, String merchantId, Integer memberLevel) {
        UserMember userMember = new UserMember();
        userMember.setAvailable(Available.NORMAL.ordinal());
        userMember.setMerchantId(merchantId);
        userMember.setMemberLevel(memberLevel);
        userMember.setCreateTime(new Date());
        userMember.setUserId(userId);
        userMemberService.addUserMember(userMember);
    }


    /**
     * 注册用户
     *
     * @param phone
     * @param registerFrom
     * @return
     */
    private User registerUser(String phone, Integer registerFrom) {
        User user = new User();
        user.setUserId(UUIDUtil.getUUID32());
        user.setRegisterTime(new Date());
        user.setRegisterFrom(registerFrom);
        user.setAvailable(Available.NORMAL.ordinal());
        if (StringUtils.isNotBlank(phone)) {
            user.setRegisterMobile(phone);
        }
        user = userService.addUser(user);
        return user;
    }

    private void verify(LoginParams loginParams) {
        if (null == loginParams) {
            BSUtil.isTrue(false, "参数错误");
        }
        if (StringUtils.isBlank(loginParams.getMerchantId())) {
            BSUtil.isTrue(false, "商户不能为空");
        }
        if (null == loginParams.getMemberLevel()) {
            BSUtil.isTrue(false, "账号等级不能为空");
        }
        if (null == loginParams.getRegisterFrom()) {
            BSUtil.isTrue(false, "注册账号来源不能为空");
        }
        RegisterSource registerSource = RegisterSource.getRegisterSourceById(loginParams.getRegisterFrom());
        if (null == registerSource) {
            BSUtil.isTrue(false, "无效的注册来源");
        }
        Channel channel = Channel.getChannelById(loginParams.getChannelId());
        if (channel == null) {
            BSUtil.isTrue(false, "无效的第三方");
        }
        switch (channel) {
            case H5:
            case MINI_APP:
                if (StringUtils.isBlank(loginParams.getWechatCode())) {
                    BSUtil.isTrue(false, "微信Code不能为空");
                }
                break;
            default:
                break;
        }
    }

    private void verifyBus(LoginParams loginParams) {
        if (null == loginParams) {
            BSUtil.isTrue(false, "参数错误");
        }
        if (StringUtils.isBlank(loginParams.getPhone())) {
            BSUtil.isTrue(false, "请输入手机号码");
        }
        if (StringUtils.isBlank(loginParams.getMsgCode())) {
            BSUtil.isTrue(false, "请输入短信验证码");
        }
        boolean success = msgSenderService.checkCode(loginParams.getPhone(), loginParams.getMsgCode(), MsgCode.REGISTER_LOGIN);
        if (!success) {
            BSUtil.isTrue(false, "验证码错误");
        }
        User user = userService.getUserByPhone(loginParams.getPhone(), Available.NORMAL);
        if (null != user && StringUtils.isNotBlank(user.getUserId())) {
            //用户存在不用验证注册的信息
            return;
        }
        if (StringUtils.isBlank(loginParams.getMerchantId())) {
            BSUtil.isTrue(false, "商户不能为空");
        }
        if (null == loginParams.getMemberLevel()) {
            BSUtil.isTrue(false, "账号等级不能为空");
        }
        if (null == loginParams.getRegisterFrom()) {
            BSUtil.isTrue(false, "注册账号来源不能为空");
        }
        RegisterSource registerSource = RegisterSource.getRegisterSourceById(loginParams.getRegisterFrom());
        if (null == registerSource) {
            BSUtil.isTrue(false, "无效的注册来源");
        }
        Channel channel = Channel.getChannelById(loginParams.getChannelId());
        if (channel == null) {
            BSUtil.isTrue(false, "无效的第三方");
        }
        switch (channel) {
            case H5:
            case MINI_APP:
                if (StringUtils.isBlank(loginParams.getWechatCode())) {
                    BSUtil.isTrue(false, "微信Code不能为空");
                }
                break;
        }
    }
}
