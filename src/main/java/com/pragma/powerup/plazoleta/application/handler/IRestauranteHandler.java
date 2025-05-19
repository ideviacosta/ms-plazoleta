package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;

public interface IRestauranteHandler {
    void crearRestaurante(RestauranteRequestDto dto, String rolCreador);
}