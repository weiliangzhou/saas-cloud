package com.wegoo.constants.msg;

/**
 * 短信验证码的类型
 */
public enum MsgCode {

    /**
     * 0: 默认类型没有任何含义
     */
    DEFAULT,
    /**
     * 1 注册登录(由于目前注册登录属于同一接口验证码类型因此统一)
     */
    REGISTER_LOGIN,
    /**
     * 2 用户实名认证
     */
    ATTESTATION,
    /**
     * 3 修改注册手机号码
     */
    MODIFY_PHONE,
    /**
     * 4 激活导入用户
     */
    ACTIVATE_USER;


    public static MsgCode getMsgCodeType(Integer code) {
        if (null == code) {
            return MsgCode.DEFAULT;
        }
        for (MsgCode msgCode : MsgCode.values()) {
            if (code == msgCode.ordinal()) {
                return msgCode;
            }
        }
        return MsgCode.DEFAULT;
    }
}
