package com.wegoo.constants.user;

/**
 * 注册来源 0:网页 1:微信-H5 2:抖音 3:公众号 4:今日头条 5:业务员
 */
public enum RegisterSource {

    /**
     * 网页注册
     */
    WEB,
    /**
     * 微信-H5
     */
    H5,
    /**
     * 抖音
     */
    DY,
    /**
     * 公众号
     */
    GZH,
    /**
     * 今日头条
     */
    JRTT,
    /**
     * 业务员
     */
    YWY;


    public static RegisterSource getRegisterSourceById(int code) {
        for (RegisterSource registerSource : RegisterSource.values()) {
            if (registerSource.ordinal() == code) {
                return registerSource;
            }
        }
        return null;
    }
}
