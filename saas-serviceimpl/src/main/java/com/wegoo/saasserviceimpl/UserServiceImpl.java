package com.wegoo.saasserviceimpl;

import com.wegoo.baseservice.BaseRedisService;
import com.wegoo.constants.Available;
import com.wegoo.constants.user.RegisterSource;
import com.wegoo.exception.BSUtil;
import com.wegoo.exception.BusinessException;
import com.wegoo.model.po.OfflineActivity;
import com.wegoo.model.po.OfflineActivityOrder;
import com.wegoo.model.po.User;
import com.wegoo.model.po.UserInfo;
import com.wegoo.saasdao.mapper.UserMapper;
import com.wegoo.saasservice.*;
import com.wegoo.utils.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 二师兄超级帅
 * @Title: UserServiceImpl
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1011:24
 */
@SuppressWarnings("AlibabaTransactionMustHaveRollback")
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private BaseRedisService baseRedisService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public User loginCheck(String phone) {
        User user = new User();
        user.setRegisterMobile(phone);
        return userMapper.selectOneByParams(user);
    }

    @Override
    public User getUserInfoRedisByToken(String token) {
        String userId = (String) baseRedisService.getString(token);
        User user = (User) baseRedisService.getObject(userId);
        return user;
    }

    @Override
    public User getUserByPhone(String phone, Available available) {
        if (StringUtils.isBlank(phone)) {
            BSUtil.isTrue(false, "请输入要查询的手机号码");
        }
        User user = new User();
        user.setRegisterMobile(phone);
        if (null != available) {
            user.setAvailable(available.ordinal());
        }
        return userMapper.selectOneByParams(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        User user = new User();
        user.setUserId(userId);
        return userMapper.selectOneByParams(user);
    }

    @Override
    public User addUser(User user) {
        verify(user);
        userMapper.insertSelective(user);
        return user;
    }

    @Override
    public String getUserShareImage(String url, String userId, String referrerId, String imgUrl, Integer productId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        String redisKey = String.format("%s_shareimg_%s", userId, productId);
        String qrUrl = stringRedisTemplate.boundValueOps(redisKey).get();
        if (StringUtils.isBlank(qrUrl)) {
            String smallImage = QRCodeUtil.createQrCode(url + "?referrer=" + referrerId, null, null);
            User user = this.getUserByUserId(userId);
            if (user == null) {
                BSUtil.isTrue(false, "无效的用户编号");
            }
            UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
            String userLogo = userInfo == null || userInfo.getLogoUrl() == null ? "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJVstlHM5ObIbTuicmpf9ib5DU62mUuuKFxe1uibiaO1icnGhtXy3TbIXPp1TQsxwUiaKhJaKwQaBt7Z3ibA/132" : userInfo.getLogoUrl();
            String realName = userInfo == null ? "" : userInfo.getRealName();
            try {
                qrUrl = QRCodeUtil.mergeImage(imgUrl, smallImage, "100", "682", userLogo, "238", "26", realName, Color.BLACK);
            } catch (Exception e) {
                log.error("生成二维码错误", e);
                BSUtil.isTrue(false, "生成二维码错误");
            }
            stringRedisTemplate.boundValueOps(redisKey).set(qrUrl, 30, TimeUnit.DAYS);
        }
        log.info("userId:" + userId + "------二维码" + qrUrl);
        return qrUrl;
    }


    @Override
    public String getMyShareImage(String url, String userId, String referrerId, String imgUrl, Integer productId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        String redisKey = String.format("%s_shareimg_%s", userId, productId);
        String qrUrl = stringRedisTemplate.boundValueOps(redisKey).get();
        if (StringUtils.isBlank(qrUrl)) {
            String smallImage = QRCodeUtil.createQrCode(url + "?referrer=" + referrerId, null, null);
            User user = this.getUserByUserId(userId);
            if (user == null) {
                BSUtil.isTrue(false, "无效的用户编号");
            }
            UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
            String userLogo = userInfo == null || userInfo.getLogoUrl() == null ? "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJVstlHM5ObIbTuicmpf9ib5DU62mUuuKFxe1uibiaO1icnGhtXy3TbIXPp1TQsxwUiaKhJaKwQaBt7Z3ibA/132" : userInfo.getLogoUrl();
            String realName = userInfo == null ? "" : userInfo.getRealName();
            try {
                qrUrl = QRCodeUtil.mergeImage(imgUrl, smallImage, "100", "682", userLogo, "238", "26", realName, Color.WHITE);
            } catch (Exception e) {
                log.error("生成二维码错误", e);
                BSUtil.isTrue(false, "生成二维码错误");
            }
            stringRedisTemplate.boundValueOps(redisKey).set(qrUrl, 30, TimeUnit.DAYS);
        }
        log.info("userId:" + userId + "------二维码" + qrUrl);
        return qrUrl;
    }

    @Override
    @Transactional
    public Boolean updatePerfectUserInfo(String userId, Integer activityId, String idCardNum, String orderNo, String realName, String phone, String address, String profession, String brand) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户编号不能为空");
        }
//        if (StringUtils.isBlank(idCardNum)) {
//            throw new BusinessException("身份证号码不能为空");
//        }
        if (StringUtils.isBlank(orderNo)) {
            throw new BusinessException("订单号不能为空");
        }
//        if (null == activityId) {
//            throw new BusinessException("活动地址不能为空");
//        }
//        if (null == realName) {
//            throw new BusinessException("真实姓名不能为空");
//        }
        if (null == phone) {
            throw new BusinessException("手机号码不能为空");
        }
        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(activityId);
        if (null == offlineActivity) {
            throw new BusinessException("无效的活动地点");
        }
        offlineActivityService.updateBuyCountById(activityId);
        //修改活动吗对应的code
        OfflineActivityOrder sysOrder = offlineActivityOrderService.selectOneByOrderNo(orderNo);
        if (null == sysOrder) {
            throw new BusinessException("无效的订单号");
        }
        offlineActivityCodeService.updateOfflineActivityCodeActivityId(sysOrder.getActivityCode(), activityId);
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        userInfoService.updateIdCardNumAndRealNameAndPhoneByUserId(userId, idCardNum, realName, phone);
        log.info("=============================" + phone + "============================" + userId);
        userMapper.updatePhoneByUserId(phone, userId);
        //修改订单上面的信息
        OfflineActivityOrder offlineActivityOrder = new OfflineActivityOrder();
        offlineActivityOrder.setRealName(userInfo.getRealName());
        offlineActivityOrder.setPhone(userInfo.getRegisterMobile());
        offlineActivityOrder.setIdCardNum(idCardNum);
        offlineActivityOrder.setActivityId(activityId);
        offlineActivityOrder.setStartTime(offlineActivity.getActivityStartTime());
        offlineActivityOrder.setEndTime(offlineActivity.getActivityEndTime());
        offlineActivityOrder.setOrderNo(orderNo);
        offlineActivityOrder.setRealName(realName);
        offlineActivityOrder.setPhone(phone);
        offlineActivityOrder.setRemark(address);
        offlineActivityOrder.setProfession(profession);
        offlineActivityOrder.setBrand(brand);
        offlineActivityOrderService.updateOfflineActivityOrderInfo(offlineActivityOrder);
        return Boolean.TRUE;
    }

    @Override
    public void updateRegisterMobileByUserId(String phone, String userId) {
        userMapper.updatePhoneByUserId(phone, userId);
    }

    private void verify(User user) {
        if (user == null) {
            BSUtil.isTrue(false, "参数异常");
        }
        if (StringUtils.isBlank(user.getUserId())) {
            BSUtil.isTrue(false, "用户标识不能为空");
        }
        if (user.getRegisterFrom() == null) {
            BSUtil.isTrue(false, "账号来源不能为空");
        }
        RegisterSource source = RegisterSource.getRegisterSourceById(user.getRegisterFrom());
        if (null == source) {
            BSUtil.isTrue(false, "无效的账号来源");
        }
    }
}
