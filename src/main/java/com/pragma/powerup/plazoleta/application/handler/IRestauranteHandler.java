package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteListadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;

public interface IRestauranteHandler {
    void crearRestaurante(RestauranteRequestDto dto, String rolCreador);
    PaginaRespuesta<RestauranteListadoResponseDto> listarRestaurantes(int page, int pageSize, String rolCreador);
}