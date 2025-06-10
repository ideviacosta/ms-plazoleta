package com.pragma.powerup.plazoleta.domain.api;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;

import java.util.List;

public interface IRestauranteService {
    void crearRestaurante(Restaurante restaurante, String rolCreador);
    List<Restaurante> listarRestaurantes(int page, int size, String rol);
}