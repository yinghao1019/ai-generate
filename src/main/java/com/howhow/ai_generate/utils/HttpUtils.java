package com.howhow.ai_generate.utils;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class HttpUtils {
    public static String getClientIpAddress(HttpServletRequest httpServletRequest) {
        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isAnyEmpty(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        // 使用 localhost 會接收到 0:0:0:0:0:0:0:1
        if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = "127.0.0.1";
        }
        return ipAddress;
    }
}
