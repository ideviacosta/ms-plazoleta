package com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PlatoEntity;

public class PlatoEntityMapper {

    public static PlatoEntity toEntity(Plato plato) {
        PlatoEntity entity = new PlatoEntity();
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
        return new Plato(
                entity.getId(),
                entity.getNombre(),
                entity.getPrecio(),
                entity.getDescripcion(),
                entity.getUrlImagen(),
                entity.getIdCategoria(),
                entity.getIdRestaurante(),
                entity.getActivo()
        );
    }
}