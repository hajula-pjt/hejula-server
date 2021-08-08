package com.hejula.server.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Value("${custom.file.path}")
    private String filePath;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns(
                    "/test/**",
                    "/user/**", //회원가입, 로그인
                    "/accommodation/**",
                    "/images/**",
                    "/token/**",
                    "/v2/api-docs",
                    "/swagger**",
                    "/swagger-resources/**",
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/webapp/**"
        );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
//                .allowedOrigins("localhost", "127.0.0.1", "127.0.0.1:3000", "http://localhost:3000", "http://localhost",
//                        "127.0.0.1:8080", "http://localhost:8080")
                .allowedOrigins("*")
//                .allowedHeaders(HttpHeaders.CONTENT_TYPE,"Set-Cookie",HttpHeaders.ACCEPT,HttpHeaders.USER_AGENT,HttpHeaders.REFERER,HttpHeaders.AUTHORIZATION)
//                .allowCredentials(true)
        .maxAge(3600 * 24);//1일
         }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/" + filePath + "/");

        //googling keyworkd : how to display storage image in spring boot
    }
}
