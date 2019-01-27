package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.ActivateInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二师兄
 */
public interface ActivateInfoMapper {
    /**
     * 通过phone 获取详情
     *
     * @param phone
     * @return
     */
    ActivateInfo getDetailByPhone(@Param("phone") String phone);

    /**
     * 通过phone 更新是否使用
     *
     * @param phone
     */
    void updateIsUsedByPhone(String phone);

    /**
     * 通过activityCode查询
     *
     * @param activityCode
     * @return
     */
    ActivateInfo getOneByActivityCode(String activityCode);

    /**
     * 通过activityCode 更新是否使用
     *
     * @param activityCode
     * @return
     */
    int updateIsUsedByCode(String activityCode);
}
