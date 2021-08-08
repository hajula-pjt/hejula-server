package com.hejula.server.config;

import com.hejula.server.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 토큰 유효성 검사를 위한 preHandle 존재
 *
 * @author jooyeon
 * @since 2021.07.17
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    // 토큰 유효성 검사
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if("localhost".equals(request.getServerName())){
            return true;
        }

        //추후 log개발시에 이곳에 넣을 것
        return jwtService.checkVerifyToken(request.getHeader("Authorization"));
    }

}
