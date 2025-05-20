package com.pragma.powerup.plazoleta.domain.api;


import com.pragma.powerup.plazoleta.domain.model.Plato;

public interface IPlatoService {
    void crearPlato(Plato plato, String rol, Long idPropietario);
}