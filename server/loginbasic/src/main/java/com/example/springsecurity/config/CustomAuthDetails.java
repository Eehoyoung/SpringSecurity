package com.example.springsecurity.config;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class CustomAuthDetails implements AuthenticationDetailsSource<HttpServletRequest, com.example.springsecurity.config.RequestInfo> {

    @Override
    public com.example.springsecurity.config.RequestInfo buildDetails(HttpServletRequest request) {
        return com.example.springsecurity.config.RequestInfo.builder()
                .remoteIp(request.getRemoteAddr())
                .sessionId(request.getSession().getId())
                .LoginTime(LocalDateTime.now())
                .build();
    }
}
