package com.pragma.powerup.plazoleta.infraestructure.output.restclient;

import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.FakeUsuarioRestClient;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRestClientTest {

    private FakeUsuarioRestClient usuarioRestClient;

    @BeforeEach
    void setUp() {
        usuarioRestClient = new FakeUsuarioRestClient();
    }

    @Test
    void obtenerUsuarioPorId_devuelveUsuarioSiExiste() {
        usuarioRestClient.setDevolverPropietario(true);

        UsuarioResponseDto resultado = usuarioRestClient.obtenerUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("PROPIETARIO", resultado.getRol());
    }

    @Test
    void obtenerUsuarioPorId_lanzaExcepcionSiNoExiste() {
        usuarioRestClient.setDevolverNull(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usuarioRestClient.obtenerUsuarioPorId(99L);
        });

        assertEquals("El usuario con id 99 no existe.", ex.getMessage());
    }
}