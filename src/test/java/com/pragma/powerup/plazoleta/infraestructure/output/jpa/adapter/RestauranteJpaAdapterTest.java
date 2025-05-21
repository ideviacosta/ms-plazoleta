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

    @Test
    void esPropietarioDelRestaurante_retornaTrue_siUsuarioEsPropietarioYRestauranteExiste() {
        Long idPropietario = 1L;
        Long idRestaurante = 2L;

        fakeUsuarioRestClient.setDevolverPropietario(true);
        when(restauranteRepository.existsByIdAndIdPropietario(idRestaurante, idPropietario)).thenReturn(true);

        boolean resultado = adapter.esPropietarioDelRestaurante(idPropietario, idRestaurante);

        assertTrue(resultado);
    }
    @Test
    void esPropietarioDelRestaurante_retornaFalse_siUsuarioNoEsPropietario() {
        fakeUsuarioRestClient.setDevolverPropietario(false); // retornará usuario con rol ADMIN

        boolean resultado = adapter.esPropietarioDelRestaurante(1L, 1L);

        assertFalse(resultado);
    }
    @Test
    void esPropietarioDelRestaurante_retornaFalse_siUsuarioNoExiste() {
        fakeUsuarioRestClient.setDevolverNull(true);

        boolean resultado = adapter.esPropietarioDelRestaurante(1L, 1L);

        assertFalse(resultado);
    }

}