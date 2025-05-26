package com.pragma.powerup.plazoleta.infraestructure.security;

import com.pragma.powerup.plazoleta.util.JwtUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configuration
public class SecurityConfig {

    @Bean
    @ConditionalOnProperty(name = "jwt.filter.enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtUtil));
        registrationBean.addUrlPatterns("/platos/*", "/restaurantes/*", "/usuarios/*");
        return registrationBean;
    }
}