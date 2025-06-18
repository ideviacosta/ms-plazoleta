package com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente;

import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.NotificacionSmsRequestDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificacionSmsClient {

    private final RestTemplate restTemplate;
    private final String urlNotificaciones;

    public NotificacionSmsClient(
            @Value("${notificaciones.url}") String urlNotificaciones,
            RestTemplateBuilder builder
    ) {
        this.restTemplate = builder.build();
        this.urlNotificaciones = urlNotificaciones;
    }

    public void enviarSms(String telefonoDestino, String mensaje) {
        NotificacionSmsRequestDto request = new NotificacionSmsRequestDto(telefonoDestino, mensaje);
        restTemplate.postForEntity(urlNotificaciones + "/notificaciones/sms", request, Void.class);
    }
}