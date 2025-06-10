package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteListadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;

import java.util.List;

public interface IRestauranteHandler {
    void crearRestaurante(RestauranteRequestDto dto, String rolCreador);
    List<RestauranteListadoResponseDto> listarRestaurantes(int page, int pageSize, String rolCreador);
}