package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.PlatoRequestMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoRequestMapperTest {

    @Test
    void toModelShouldMapCorrectly() {
        PlatoRequestDto dto = new PlatoRequestDto();
        dto.setNombre("Empanada");
        dto.setPrecio(3000);
        dto.setDescripcion("Con ají");
        dto.setUrlImagen("http://img.com/empanada.jpg");
        dto.setIdCategoria(1L);
        dto.setIdRestaurante(5L);

        Plato plato = PlatoRequestMapper.toModel(dto);

        assertEquals("Empanada", plato.getNombre());
        assertEquals(3000, plato.getPrecio());
        assertEquals("Con ají", plato.getDescripcion());
        assertEquals("http://img.com/empanada.jpg", plato.getUrlImagen());
        assertEquals(1L, plato.getIdCategoria());
        assertEquals(5L, plato.getIdRestaurante());
    }
}