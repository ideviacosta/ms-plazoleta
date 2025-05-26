package com.pragma.powerup.plazoleta.config;

import jakarta.servlet.Filter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DisableJwtFilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> disableJwtFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter((request, response, chain) -> chain.doFilter(request, response)); // Filtro vacío
        registration.setEnabled(false); // No registrar
        return registration;
    }
}