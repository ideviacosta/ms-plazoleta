package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.PlatoRequestMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler {

    private final IPlatoService platoService;

    @Override
    public void crearPlato(PlatoRequestDto dto, String rol, Long idPropietario) {
        platoService.crearPlato(PlatoRequestMapper.toModel(dto), rol, idPropietario);
    }

    @Override
    public void modificarPlato(Long idPlato, PlatoUpdateRequestDto dto, String rol, Long idPropietario) {
        try {
            platoService.modificarPlato(idPlato, dto.getPrecio(), dto.getDescripcion(), rol, idPropietario);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime en consola el error real
            throw e; // repropaga para que lo capture el handler global si lo tienes
        }
    }


}