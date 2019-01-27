package com.wegoo.saas.member.filter;

import com.alibaba.fastjson.JSON;
import com.wegoo.baseresult.BaseResult;
import com.wegoo.constants.BaseResultConstants;
import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.tokenManager.RedisTokenManagerImpl;
import com.wegoo.tokenManager.TokenModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 二师兄超级帅
 * @Title: TokenFilter
 * @ProjectName parent
 * @Description: token过滤器
 * @date 2018/7/615:26
 */
@Order(1)
// 重点
@WebFilter(filterName = "tokenFilter", urlPatterns = "/*")
@Slf4j
public class TokenFilter implements Filter {

    @Autowired
    private RedisTokenManagerImpl manager;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest h_request = (HttpServletRequest) request;

        String token = h_request.getHeader("token");
        String requestURL = h_request.getRequestURL().toString();
        log.info("<<token>>请求url:" + requestURL + "  token:" + token);
        CurrentUserInfoContext.clearThreadVariable();
        if (!requestURL.contains("/auth")) {
            if (StringUtils.isNotBlank(token)) {
                TokenModel tokenModel = manager.getToken(token);
                String userId = tokenModel.getUserId();
                CurrentUserInfoContext.setUserID(userId);
            }
            chain.doFilter(request, response);
            return;
        }
        // 验证token
        // token="nprI2s/ITAaGaLJfL+QkZZYKhMAH3C3PkQEbpv+qQwOFG7hECEPeR4lKz5NTpQ177Sk1MdBj1GEjyNM2V0G2Nbj3FfKz6X+C";
        // 这里token如果接收有空格的地方，，那就是+号没有处理好。。可以考虑变成%2B
        if (StringUtils.isBlank(token)) {
            response.getWriter().println(JSON.toJSONString(new BaseResult(BaseResultConstants.HTTP_CODE_900, BaseResultConstants.HTTP_CODE_900_VALUE, null)));
            return;
        }
        token = token.replaceAll(" ", "+");
        TokenModel model = manager.getToken(token);
        if (manager.checkFeginToken(model)) {
            // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
            // request.setAttribute(Constants.CURRENT_USER_ID, model.getName());
            // app请求就一次，所有session没有用处 除非pc
            // session.setAttribute(Constants.CURRENT_USER_ID,
            // model.getuserId());
            chain.doFilter(request, response);
        } else {
            // 如果验证token失败
            response.getWriter().println(JSON.toJSONString(new BaseResult(BaseResultConstants.HTTP_CODE_900, BaseResultConstants.HTTP_CODE_900_VALUE, null)));
            return;
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}