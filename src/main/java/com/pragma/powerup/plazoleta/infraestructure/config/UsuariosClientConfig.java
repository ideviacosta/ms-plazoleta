package com.pragma.powerup.plazoleta.infraestructure.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuariosClientConfig {

    @Value("${usuarios.url}")
    private String usuariosUrl;

    public String getUsuariosUrl() {
        return usuariosUrl;
    }
}