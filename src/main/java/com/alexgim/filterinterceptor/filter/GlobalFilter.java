package com.alexgim.filterinterceptor.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = "/user/*")
@Slf4j
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        LocalDateTime start = LocalDateTime.now();

        chain.doFilter(httpServletRequest, httpServletResponse);

        LocalDateTime end = LocalDateTime.now();

        String reqContent = new String(httpServletRequest.getContentAsByteArray());
        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int status = httpServletResponse.getStatus();

        httpServletResponse.copyBodyToResponse();

        log.info("request : {}, response : {}, status : {}", reqContent, resContent, status);
        log.info("start : {}, end : {}", start, end);

    }
}
