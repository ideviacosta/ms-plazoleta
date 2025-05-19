package com.pragma.powerup.plazoleta.domain.api;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;

public interface IRestauranteService {
    void crearRestaurante(Restaurante restaurante, String rolCreador);
}