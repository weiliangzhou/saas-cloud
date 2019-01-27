package com.wegoo.saasservice;

import com.wegoo.constants.msg.MsgCode;

/**
 * @author 二师兄超级帅
 * @Title: MessageSenderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1413:57
 */
public interface MsgSenderService {
    /**
     * 发送验证码
     *
     * @param phone   接受的手机号
     * @param msgCode 验证码类型
     */
    public void sendCode(String phone, MsgCode msgCode);

    public void sendMsg(String phone, String msg);

    /**
     * 验证验证码是否正确
     *
     * @param phone   手机号码
     * @param code    验证码
     * @param busCode 验证码类型
     */
    public boolean checkCode(String phone, String code, MsgCode busCode);
}
