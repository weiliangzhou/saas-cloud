package com.wegoo.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 二师兄超级帅
 * @Title: IpKit
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/916:58
 */
public class IpKit {
    public IpKit() {
    }

    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getRealIpV2(HttpServletRequest request) {
        String accessIP = request.getHeader("x-forwarded-for");
        return null == accessIP ? request.getRemoteAddr() : accessIP;
    }
}
