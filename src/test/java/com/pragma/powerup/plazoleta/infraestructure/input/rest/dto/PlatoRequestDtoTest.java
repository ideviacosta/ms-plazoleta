package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoRequestDtoTest {

    @Test
    void gettersAndSettersShouldWork() {
        PlatoRequestDto dto = new PlatoRequestDto();
        dto.setNombre("Arepa");
        dto.setPrecio(5000);
        dto.setDescripcion("Con queso");
        dto.setUrlImagen("http://img.com/arepa.jpg");
        dto.setIdCategoria(1L);
        dto.setIdRestaurante(2L);

        assertEquals("Arepa", dto.getNombre());
        assertEquals(5000, dto.getPrecio());
        assertEquals("Con queso", dto.getDescripcion());
        assertEquals("http://img.com/arepa.jpg", dto.getUrlImagen());
        assertEquals(1L, dto.getIdCategoria());
        assertEquals(2L, dto.getIdRestaurante());
    }
}