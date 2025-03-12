package com.howhow.ai_generate.interceptor;

import com.howhow.ai_generate.constant.LogParam;
import com.howhow.ai_generate.utils.HttpUtils;
import com.howhow.ai_generate.utils.TimeUtils;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import java.time.OffsetDateTime;
import java.util.UUID;

public class MdcWebRequestInterceptor implements WebRequestInterceptor {
    // Request處理前先呼叫preHandle()
    @Override
    public void preHandle(WebRequest webRequest) {
        MDC.put(LogParam.REQUEST_OID, UUID.randomUUID().toString());
        HttpServletRequest httpServletRequest =
                ((DispatcherServletWebRequest) webRequest).getRequest();
        String ipAddress = HttpUtils.getClientIpAddress(httpServletRequest);
        String acceptLanguage = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (acceptLanguage != null) {
            MDC.put(HttpHeaders.ACCEPT_LANGUAGE, acceptLanguage);
        }

        MDC.put("ip", ipAddress);
        MDC.put("req.remoteHost", httpServletRequest.getRemoteHost());
        MDC.put("req.requestURI", httpServletRequest.getRequestURI());
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        if (requestURL != null) {
            MDC.put("req.requestURL", requestURL.toString());
        }
        MDC.put("req.method", httpServletRequest.getMethod());
        MDC.put("req.queryString", httpServletRequest.getQueryString());
        MDC.put("req.userAgent", httpServletRequest.getHeader("User-Agent"));
        MDC.put("req.xForwardedFor", httpServletRequest.getHeader("X-Forwarded-For"));

        OffsetDateTime now = TimeUtils.getTaiwanNow();
        String yearMonth =
                String.format("%s-%s", now.getYear(), String.format("%02d", now.getMonthValue()));
        MDC.put(LogParam.LOG_FILE_DISCRIMINATOR, yearMonth + "/User");
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) {}

    // 無論Request處理成功或拋出錯誤，最後都會呼叫afterCompletion()，afterCompletion()結束後，Request相關資料會被GC
    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) {
        MDC.clear();
    }
}
