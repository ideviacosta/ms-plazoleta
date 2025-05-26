package com.pragma.powerup.plazoleta.infraestructure.mock;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MockAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
            private final Map<String, Object> attrs = new HashMap<>();

            {
                attrs.put("usuarioRol", "PROPIETARIO");
                attrs.put("usuarioId", 1L);
            }

            @Override
            public Object getAttribute(String name) {
                return attrs.getOrDefault(name, super.getAttribute(name));
            }
        };

        chain.doFilter(wrapper, response);
    }
}