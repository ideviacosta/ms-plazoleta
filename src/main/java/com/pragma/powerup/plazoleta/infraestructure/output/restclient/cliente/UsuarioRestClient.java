package com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente;

import com.pragma.powerup.plazoleta.infraestructure.config.UsuariosClientConfig;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UsuarioRestClient {

    private final RestTemplate restTemplate;
    private final UsuariosClientConfig config;

    public UsuarioResponseDto obtenerUsuarioPorId(Long idUsuario) {
        try {
            return restTemplate.getForObject(config.getUsuariosUrl() + "/usuarios/" + idUsuario, UsuarioResponseDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("El usuario con id " + idUsuario + " no existe.");
        }
    }


}