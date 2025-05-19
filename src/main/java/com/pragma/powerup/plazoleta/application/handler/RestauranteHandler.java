package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.RestauranteRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteHandler implements IRestauranteHandler {

    private final IRestauranteService restauranteService;

    @Override
    public void crearRestaurante(RestauranteRequestDto dto, String rolCreador) {
        Restaurante restaurante = RestauranteRequestMapper.toModel(dto);
        restauranteService.crearRestaurante(restaurante, rolCreador);
    }
}