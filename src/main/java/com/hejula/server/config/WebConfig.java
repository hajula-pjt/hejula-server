package com.hejula.server.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
        //.addPathPatterns("/") // 숙박 예약만 인터셉터에 추가하도록 처리
               /* .excludePathPatterns(
                "/test/**",
                "/user/**", //회원가입, 로그인
                "/accommodation/nonAuth/**", 
                "/token/**",
                "/v2/api-docs",
                "/swagger**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/webapp/**"
        );*/
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("localhost", "127.0.0.1", "127.0.0.1:3000", "http://localhost:3000", "http://localhost",
                        "127.0.0.1:8080", "http://localhost:8080")
//                .allowedOrigins("*")
                .allowedHeaders(HttpHeaders.CONTENT_TYPE,"Set-Cookie",HttpHeaders.ACCEPT,HttpHeaders.USER_AGENT,HttpHeaders.REFERER,HttpHeaders.AUTHORIZATION)
                .allowCredentials(true)
        .maxAge(3600 * 24);//1일
         }
}