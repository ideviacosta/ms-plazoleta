package com.pragma.powerup.plazoleta.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestFilterConfig {

    @Bean
    public FilterRegistrationBean<?> disableJwtFilter() {
        // Este bean anula el JwtFilter en tests
        FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}
