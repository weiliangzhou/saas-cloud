package com.wegoo.saasservice;

import com.wegoo.model.po.UserReferrer;
import com.wegoo.model.vo.user.UserInfoAllVo;

public interface UserReferrerService {
    /**
     * 绑定用户上下级关系
     *
     * @param userId    用户编号
     * @param userRef   绑定的上级编号
     * @param merchatId 商户号
     */
    UserReferrer bindUserRef(String userId, String userRef, String merchatId);

    /**
     * 添加用户绑定关系
     */
    UserReferrer addUserReferrer(UserReferrer userReferrer);

    /**
     * 通过用户编号 跟 商户号查询用户绑定上下级关系
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     */
    UserReferrer getUserReferrerByUserIdAndMerchant(String userId, String merchantId);

    /**
     * 获取用户绑定的上级用户信息
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     */
    UserInfoAllVo getUserReferrerInfo(String userId, String merchantId);

    /**
     * 查询用户(业务员)绑定了多少个下线
     *
     * @param userId     用户标识
     * @param merchantId 商户号
     * @return 统计数
     */
    Integer getUserBindCount(String userId, String merchantId);

    /**
     * 购买之后 锁定关系
     *
     * @param userId
     * @param merchantId
     */
    void updateIsBuyByUserIdAndMerchantId(String userId, String merchantId);

    /**
     * 激活用户 锁定上级关系
     *
     * @param userId
     * @param merchantId
     */
    void updateIsBuyAndReferrerByUserIdAndMerchantId(String referrer, String userId, String merchantId);


    /**
     * 更换绑定业务员
     *
     * @param userId
     * @param userRef
     * @param merchantId
     * @return
     */
    UserReferrer changeUserReferrer(String userId, String userRef, String merchantId);

    /**
     * 业务员清空上级
     *
     * @param userId
     */
    void removeReferrer(String userId);
}
