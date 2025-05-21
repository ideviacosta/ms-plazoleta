package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;

public interface IPlatoHandler {
    void crearPlato(PlatoRequestDto dto, String rol, Long idPropietario);
    void modificarPlato(Long idPlato, PlatoUpdateRequestDto dto, String rol, Long idPropietario);

}