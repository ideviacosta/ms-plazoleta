package com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PlatoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoEntityMapperTest {

    @Test
    void toEntity_shouldMapCorrectly() {
        Plato model = Plato.builder()
                .id(1L)
                .nombre("Pizza")
                .precio(10000)
                .descripcion("Rica")
                .urlImagen("img.jpg")
                .idCategoria(1L)
                .idRestaurante(2L)
                .activo(true)
                .build();

        PlatoEntity entity = PlatoEntityMapper.toEntity(model);

        assertNotNull(entity);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getNombre(), entity.getNombre());
        assertEquals(model.getPrecio(), entity.getPrecio());
        assertEquals(model.getDescripcion(), entity.getDescripcion());
        assertEquals(model.getUrlImagen(), entity.getUrlImagen());
        assertEquals(model.getIdCategoria(), entity.getIdCategoria());
        assertEquals(model.getIdRestaurante(), entity.getIdRestaurante());
        assertEquals(model.getActivo(), entity.getActivo());
    }

    @Test
    void toModel_shouldMapCorrectly() {
        PlatoEntity entity = new PlatoEntity();
        entity.setId(1L);
        entity.setNombre("Bandeja Paisa");
        entity.setPrecio(20000);
        entity.setDescripcion("Con todo");
        entity.setUrlImagen("http://img.com/paisa.jpg");
        entity.setIdCategoria(2L);
        entity.setIdRestaurante(3L);
        entity.setActivo(true);

        Plato model = PlatoEntityMapper.toModel(entity);

        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getNombre(), model.getNombre());
        assertEquals(entity.getPrecio(), model.getPrecio());
        assertEquals(entity.getDescripcion(), model.getDescripcion());
        assertEquals(entity.getUrlImagen(), model.getUrlImagen());
        assertEquals(entity.getIdCategoria(), model.getIdCategoria());
        assertEquals(entity.getIdRestaurante(), model.getIdRestaurante());
        assertEquals(entity.getActivo(), model.getActivo());
    }
}
