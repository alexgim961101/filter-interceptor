package com.alexgim.filterinterceptor.interceptor;

import com.alexgim.filterinterceptor.annotation.Auth;
import com.alexgim.filterinterceptor.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContentCachingRequestWrapper contentCachingRequestWrapper = (ContentCachingRequestWrapper) request;
        ContentCachingResponseWrapper contentCachingResponseWrapper = (ContentCachingResponseWrapper) response;

        URI uri = UriComponentsBuilder.fromUriString(contentCachingRequestWrapper.getRequestURI())
                .query(contentCachingRequestWrapper.getQueryString())
                .build().toUri();

        log.info("request url : {}", uri);

        boolean hasAnnotation = checkAnnotation(handler, Auth.class);
        log.info("hasAnnotation : {}", hasAnnotation);

        if(hasAnnotation){
            String query = uri.getQuery();
            if(query.equals("name=alexgim")) return true;
            return false;
        }

        return false;
    }

    private boolean checkAnnotation(Object handler, Class clazz){
        if(handler instanceof ResourceHttpRequestHandler) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if(handlerMethod.getMethodAnnotation(clazz) != null) return true;

        throw new AuthException();
    }
}
