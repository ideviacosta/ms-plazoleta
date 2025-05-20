package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;

public interface IPlatoHandler {
    void crearPlato(PlatoRequestDto dto, String rol, Long idPropietario);
}