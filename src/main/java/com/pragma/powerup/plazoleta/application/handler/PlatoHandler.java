package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.PlatoRequestMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler {

    private final IPlatoService platoService;

    @Override
    public void crearPlato(PlatoRequestDto dto, String rol, Long idPropietario) {
        platoService.crearPlato(PlatoRequestMapper.toModel(dto), rol, idPropietario);
    }
}