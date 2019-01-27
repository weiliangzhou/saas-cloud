package com.wegoo.currentUser;

import com.wegoo.tokenManager.RedisTokenManagerImpl;
import com.wegoo.tokenManager.TokenModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by 二师兄超级帅 on 2018/10/17.
 */
@Slf4j
public class TransmitUserInfoFilter implements Filter {

    @Autowired
    private RedisTokenManagerImpl tokenManager;

    public TransmitUserInfoFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.initUserInfo((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    private void initUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            try {
                token = URLDecoder.decode(token, "UTF-8");
                //将UserInfo放入上下文中
                CurrentUserInfoContext.setToken(token);
            } catch (UnsupportedEncodingException e) {
                log.error("init userInfo error", e);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
