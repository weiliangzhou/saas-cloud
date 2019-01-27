package com.wegoo.currentUser;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by 二师兄超级帅 on 2018/10/17.
 */
@Slf4j
public class TransmitUserInfoFeighClientIntercepter implements RequestInterceptor {

    public TransmitUserInfoFeighClientIntercepter() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //从应用上下文中取出user信息，放入Feign的请求头中
        String token = CurrentUserInfoContext.getToken();
        if (StringUtils.isNotBlank(token)) {
            try {
                requestTemplate.header("token", new String[]{URLDecoder.decode(token, "UTF-8")});
            } catch (UnsupportedEncodingException e) {
                log.error("用户信息设置错误", e);
            }
        }
    }
}
