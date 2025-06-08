package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.PlatoRequestMapper;



public class PlatoHandler implements IPlatoHandler {

    private final IPlatoService platoService;

    public PlatoHandler(IPlatoService platoService) {
        this.platoService = platoService;
    }

    @Override
    public void crearPlato(PlatoRequestDto dto, String rol, Long idPropietario) {
        platoService.crearPlato(PlatoRequestMapper.toModel(dto), rol, idPropietario);
    }

    @Override
    public void modificarPlato(Long idPlato, PlatoUpdateRequestDto dto, String rol, Long idPropietario) {
        try {
            platoService.modificarPlato(idPlato, dto.getPrecio(), dto.getDescripcion(), rol, idPropietario);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void cambiarEstadoPlato(Long idPlato, boolean habilitar, String rol, Long idPropietario) {
        platoService.cambiarEstadoPlato(idPlato, habilitar, rol, idPropietario);
    }


}