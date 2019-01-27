package com.wegoo.saasservice;

import com.wegoo.constants.Available;
import com.wegoo.model.po.User;

/**
 * @author 二师兄超级帅
 * @Title: UserService
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/10/1011:22
 */
public interface UserService {
    User loginCheck(String phone);

    User getUserInfoRedisByToken(String token);

    /**
     * <br> 通过手机号码查询用户</br>
     * <br> available 为空表示忽略查询条件</br>
     *
     * @param phone     手机号码
     * @param available 是否删除
     */
    User getUserByPhone(String phone, Available available);

    /**
     * 根据标识获取用户
     *
     * @param userId 用户编号
     */
    User getUserByUserId(String userId);

    /**
     * 添加用户信息
     *
     * @param user 用户信息
     */
    User addUser(User user);

    /**
     * 获取用户分享图片
     *
     * @param userId     用户编号
     * @param imgUrl     背景图片
     * @param referrerId 推荐人Id
     * @param productId  每个商品都要有单独的分享页面 值用于区分缓存
     * @return 图片路径
     */
    String getUserShareImage(String url, String userId, String referrerId, String imgUrl, Integer productId);

    /**
     * 我的推广海报
     *
     * @param url
     * @param userId
     * @param referrerId
     * @param imgUrl
     * @param productId
     * @return
     */
    String getMyShareImage(String url, String userId, String referrerId, String imgUrl, Integer productId);

    /**
     * 用户购买完成后完善用户信息
     *
     * @param userId     用户编号
     * @param activityId 活动地址编号
     * @param idCardNum  身份证号
     * @param orderNo    订单号
     * @param realName   真实姓名
     * @param address    收货地址
     */
    Boolean updatePerfectUserInfo(String userId, Integer activityId, String idCardNum, String orderNo, String realName, String phone, String address, String profession, String brand);

    /**
     * 更新用户注册手机号
     *
     * @param phone
     */

    void updateRegisterMobileByUserId(String phone, String userId);
}
