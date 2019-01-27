package com.wegoo.constants.user;

/**
 * 账号关联第三方
 */
public enum Channel {



    /**
     * 网页注册
     */
    WEB,
    /**
     * 微信-H5
     */
    H5,
    /**
     * 微信小程序
     */
    MINI_APP,
    /**
     * 线下导入
     */
    OFFLINE,
    /**
     * IOS
     */
    IOS,
    /**
     * 安卓
     */
    ANDROID;


    public static Channel getChannelById(Integer code) {
        if (null == code) {
            return null;
        }
        for (Channel channel : Channel.values()) {
            if (channel.ordinal() == code) {
                return channel;
            }
        }
        return null;
    }
}
