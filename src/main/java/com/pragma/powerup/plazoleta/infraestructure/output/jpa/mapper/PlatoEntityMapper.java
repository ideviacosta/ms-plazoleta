package com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PlatoEntity;

public class PlatoEntityMapper {

    public static PlatoEntity toEntity(Plato plato) {
        PlatoEntity entity = new PlatoEntity();
        entity.setId(plato.getId());
        entity.setNombre(plato.getNombre());
        entity.setPrecio(plato.getPrecio());
        entity.setDescripcion(plato.getDescripcion());
        entity.setUrlImagen(plato.getUrlImagen());
        entity.setIdCategoria(plato.getIdCategoria());
        entity.setIdRestaurante(plato.getIdRestaurante());
        entity.setActivo(plato.getActivo());
        return entity;
    }

    public static Plato toModel(PlatoEntity entity) {
        Plato plato = Plato.builder().build();
        plato.setId(entity.getId());
        plato.setNombre(entity.getNombre());
        plato.setPrecio(entity.getPrecio());
        plato.setDescripcion(entity.getDescripcion());
        plato.setUrlImagen(entity.getUrlImagen());
        plato.setIdCategoria(entity.getIdCategoria());
        plato.setIdRestaurante(entity.getIdRestaurante());
        plato.setActivo(entity.getActivo());
        return plato;
    }
    private PlatoEntityMapper() {}
}