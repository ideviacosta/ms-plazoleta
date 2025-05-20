package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IRestauranteRepository;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.FakeUsuarioRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RestauranteJpaAdapterTest {

    private IRestauranteRepository restauranteRepository;
    private FakeUsuarioRestClient fakeUsuarioRestClient;
    private RestauranteJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        restauranteRepository = mock(IRestauranteRepository.class);
        fakeUsuarioRestClient = new FakeUsuarioRestClient();
        adapter = new RestauranteJpaAdapter(restauranteRepository, fakeUsuarioRestClient);
    }

    @Test
    void propietarioExisteYEsValido_retornaTrue_siUsuarioEsPropietario() {
        fakeUsuarioRestClient.setDevolverPropietario(true);
        assertTrue(adapter.propietarioExisteYEsValido(1L));
    }

    @Test
    void propietarioExisteYEsValido_retornaFalse_siUsuarioNoExiste() {
        fakeUsuarioRestClient.setDevolverNull(true);
        assertFalse(adapter.propietarioExisteYEsValido(99L));
    }

    @Test
    void guardarRestaurante_guardaEntity() {
        Restaurante restaurante = new Restaurante(null, "Test", "123", "Calle", "+57", "logo", 1L);
        adapter.guardarRestaurante(restaurante);
        verify(restauranteRepository).save(any());
    }
}