//package com.examination.quiz.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignSecurityConfig {
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            var authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
//                String token = jwtAuth.getToken().getTokenValue();
//                requestTemplate.header("Authorization", "Bearer " + token);
//            }
//        };
//    }
//}