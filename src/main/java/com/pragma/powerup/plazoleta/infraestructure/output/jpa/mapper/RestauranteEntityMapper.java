package com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.RestauranteEntity;

public class RestauranteEntityMapper {

    public static RestauranteEntity toEntity(Restaurante r) {
        RestauranteEntity e = new RestauranteEntity();
        e.setNombre(r.getNombre());
        e.setNit(r.getNit());
        e.setDireccion(r.getDireccion());
        e.setTelefono(r.getTelefono());
        e.setUrlLogo(r.getUrlLogo());
        e.setIdPropietario(r.getIdPropietario());
        return e;
    }

    public static Restaurante toModel(RestauranteEntity e) {
        Restaurante r = new Restaurante();
        r.setId(e.getId());
        r.setNombre(e.getNombre());
        r.setNit(e.getNit());
        r.setDireccion(e.getDireccion());
        r.setTelefono(e.getTelefono());
        r.setUrlLogo(e.getUrlLogo());
        r.setIdPropietario(e.getIdPropietario());
        return r;
    }
}