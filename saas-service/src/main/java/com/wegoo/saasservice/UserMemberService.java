package com.wegoo.saasservice;

import com.wegoo.model.po.UserMember;

public interface UserMemberService {
    /**
     * 添加用户关联商户
     */
    UserMember addUserMember(UserMember userMember);

    /**
     * 获取用户所在商户号的等级
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     */
    UserMember getUserMemberByUserIdAndMerchatId(String userId, String merchantId);

    /**
     * 修改用户等级
     *
     * @param userId      用户编号
     * @param merchantId  所属商户
     * @param memberLevel 修改等级
     */
    Boolean updateMemberLevel(String userId, String merchantId, Integer memberLevel);
}
