package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoListadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;

import java.util.List;

public interface IPlatoHandler {
    void crearPlato(PlatoRequestDto dto, String rol, Long idPropietario);
    void modificarPlato(Long idPlato, PlatoUpdateRequestDto dto, String rol, Long idPropietario);
    void cambiarEstadoPlato(Long idPlato, boolean habilitar, String rol, Long idPropietario);
    List<PlatoListadoResponseDto> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int page, int size, String rol);
}