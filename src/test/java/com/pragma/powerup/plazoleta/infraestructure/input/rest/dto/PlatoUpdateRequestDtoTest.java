package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoUpdateRequestDtoTest {

    @Test
    void gettersAndSettersShouldWork() {
        PlatoUpdateRequestDto dto = new PlatoUpdateRequestDto();
        dto.setPrecio(18000);
        dto.setDescripcion("Actualizada");

        assertEquals(18000, dto.getPrecio());
        assertEquals("Actualizada", dto.getDescripcion());
    }
}