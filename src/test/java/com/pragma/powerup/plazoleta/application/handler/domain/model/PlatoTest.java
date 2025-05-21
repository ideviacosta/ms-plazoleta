package com.pragma.powerup.plazoleta.application.handler.domain.model;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoTest {

    @Test
    void testGettersSettersPlato() {
        Plato plato = new Plato();
        plato.setNombre("Sopa");
        plato.setPrecio(10000);
        plato.setDescripcion("Casera");
        plato.setUrlImagen("http://img.com/sopa.jpg");
        plato.setIdCategoria(2L);
        plato.setIdRestaurante(3L);
        plato.setActivo(true);

        assertEquals("Sopa", plato.getNombre());
        assertEquals(10000, plato.getPrecio());
        assertEquals("Casera", plato.getDescripcion());
        assertEquals("http://img.com/sopa.jpg", plato.getUrlImagen());
        assertEquals(2L, plato.getIdCategoria());
        assertEquals(3L, plato.getIdRestaurante());
        assertTrue(plato.getActivo());
    }
}