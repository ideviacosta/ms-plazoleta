package com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;

public class PlatoRequestMapper {

    public static Plato toModel(PlatoRequestDto dto) {
        Plato plato = Plato.builder().build();
        plato.setId(null);
        plato.setNombre(dto.getNombre());
        plato.setDescripcion(dto.getDescripcion());
        plato.setPrecio(dto.getPrecio());
        plato.setUrlImagen(dto.getUrlImagen());
        plato.setIdCategoria(dto.getIdCategoria());
        plato.setIdRestaurante(dto.getIdRestaurante());
        plato.setActivo(null);
        return plato;
    }

    private PlatoRequestMapper() {
    }
}