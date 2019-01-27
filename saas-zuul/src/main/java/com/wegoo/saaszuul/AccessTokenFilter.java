package com.wegoo.saaszuul;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.wegoo.baseresult.BaseResult;
import com.wegoo.tokenManager.TokenModel;
import com.wegoo.tokenManager.RedisTokenManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 二师兄超级帅 on 2018/10/15.
 */
@Slf4j
public class AccessTokenFilter extends ZuulFilter {
    @Autowired
    private RedisTokenManagerImpl manager;

    @Override
    public String filterType() {
        return "pre";//前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;//优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;//是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestUrl = request.getRequestURI();
        String token = request.getHeader("token");
        log.info("<<token>>请求url:" + requestUrl + "  token:" + token);
        if (!requestUrl.contains("/auth")) {
            ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);// 设值，可以在多个过滤器时使用
            return null;
        }
        token = token.replaceAll(" ", "+");
        TokenModel model = manager.getToken(token);
        if (manager.checkToken(model)) {//暂时简单化测试
            ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);// 设值，可以在多个过滤器时使用
            return null;
        } else {
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(200);// 返回错误码
            BaseResult baseResult=new BaseResult();
            baseResult.setReCode("900");
            baseResult.setReMsg("token无效");
            ctx.setResponseBody(JSON.toJSONString(baseResult));// 返回错误内容
            ctx.set("isSuccess", true);
            return JSON.toJSONString(baseResult);
        }
    }
}
