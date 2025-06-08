package com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;

public class RestauranteRequestMapper {

    public static Restaurante toModel(RestauranteRequestDto dto) {
        Restaurante r = Restaurante.builder().build();
        r.setNombre(dto.getNombre());
        r.setNit(dto.getNit());
        r.setDireccion(dto.getDireccion());
        r.setTelefono(dto.getTelefono());
        r.setUrlLogo(dto.getUrlLogo());
        r.setIdPropietario(dto.getIdPropietario());
        return r;
    }
    private RestauranteRequestMapper() {
    }
}