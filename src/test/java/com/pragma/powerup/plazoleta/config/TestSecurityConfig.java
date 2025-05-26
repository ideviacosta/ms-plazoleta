package com.pragma.powerup.plazoleta.config;
import com.pragma.powerup.plazoleta.util.JwtUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public JwtUtil jwtUtil() {
        // Esta clave debe ser válida (32+ caracteres) para pruebas, aunque el token no se use
        return new JwtUtil("clave-super-secreta-de-al-menos-32-caracteres");
    }
}