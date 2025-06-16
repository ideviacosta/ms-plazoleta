package com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper;

import com.pragma.powerup.plazoleta.domain.model.EmpleadoRestaurante;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.EmpleadoRestauranteDto;

public class EmpleadoRestauranteMapper {

    public static EmpleadoRestaurante toModel(EmpleadoRestauranteDto dto) {
        return new EmpleadoRestaurante(dto.getIdEmpleado(), dto.getIdRestaurante());
    }

    public static EmpleadoRestauranteDto toDto(EmpleadoRestaurante model) {
        return new EmpleadoRestauranteDto(model.getIdEmpleado(), model.getIdRestaurante());
    }
}