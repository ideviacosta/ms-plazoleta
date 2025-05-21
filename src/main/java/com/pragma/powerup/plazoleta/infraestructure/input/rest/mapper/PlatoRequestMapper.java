package com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;

public class PlatoRequestMapper {

    public static Plato toModel(PlatoRequestDto dto) {
        return new Plato(
                null,
                dto.getNombre(),
                dto.getPrecio(),
                dto.getDescripcion(),
                dto.getUrlImagen(),
                dto.getIdCategoria(),
                dto.getIdRestaurante(),
                null
        );
    }
}