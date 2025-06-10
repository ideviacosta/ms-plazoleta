package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestauranteUseCaseTest {

    private IRestaurantePersistencePort persistencePort;
    private RestauranteUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IRestaurantePersistencePort.class);
        useCase = new RestauranteUseCase(persistencePort);
    }

    @Test
    void crearRestaurante_deberiaGuardar_siRolEsAdministradorYPropietarioValido() {
        Restaurante restaurante = Restaurante.builder()
                .nombre("Burgers")
                .nit("123")
                .direccion("Calle 1")
                .telefono("+573001112233")
                .urlLogo("logo.png")
                .idPropietario(1L)
                .build();

        when(persistencePort.propietarioExisteYEsValido(1L)).thenReturn(true);

        assertDoesNotThrow(() -> useCase.crearRestaurante(restaurante, "ADMINISTRADOR"));

        verify(persistencePort).guardarRestaurante(any(Restaurante.class));
    }

    @Test
    void crearRestaurante_lanzaExcepcion_siRolNoEsAdministrador() {
        Restaurante restaurante = Restaurante.builder()
                .nombre("Burgers")
                .nit("123")
                .direccion("Calle 1")
                .telefono("+573001112233")
                .urlLogo("logo.png")
                .idPropietario(1L)
                .build();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> useCase.crearRestaurante(restaurante, "PROPIETARIO"));

        assertTrue(ex.getMessage().contains("Se requiere rol"));

        verify(persistencePort, never()).guardarRestaurante(any());
    }

    @Test
    void crearRestaurante_lanzaExcepcion_siPropietarioNoEsValido() {
        Restaurante restaurante = Restaurante.builder()
                .nombre("Burgers")
                .nit("123")
                .direccion("Calle 1")
                .telefono("+573001112233")
                .urlLogo("logo.png")
                .idPropietario(99L)
                .build();

        when(persistencePort.propietarioExisteYEsValido(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> useCase.crearRestaurante(restaurante, "ADMINISTRADOR"));

        assertEquals("Propietario inválido: no existe o no tiene rol PROPIETARIO", ex.getMessage());
        verify(persistencePort, never()).guardarRestaurante(any());
    }

    @Test
    void listarRestaurantes_deberiaRetornarListaOrdenada_siRolEsCliente() {
        // Arrange
        Restaurante r1 = Restaurante.builder().nombre("Arepas").urlLogo("url1").build();
        Restaurante r2 = Restaurante.builder().nombre("Burgers").urlLogo("url2").build();
        List<Restaurante> mockList = List.of(r1, r2);

        when(persistencePort.obtenerRestaurantesOrdenados(0, 2))
                .thenReturn(new PageImpl<>(mockList));

        // Act
        List<Restaurante> resultado = useCase.listarRestaurantes(0, 2, "CLIENTE");

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Arepas", resultado.get(0).getNombre());
        assertEquals("Burgers", resultado.get(1).getNombre());
    }

    @Test
    void listarRestaurantes_deberiaLanzarExcepcion_siRolNoEsCliente() {
        // Arrange
        String rolNoCliente = "EMPLEADO";

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> useCase.listarRestaurantes(0, 5, rolNoCliente));

        assertTrue(ex.getMessage().contains("Se requiere rol: CLIENTE"));
        verify(persistencePort, never()).obtenerRestaurantesOrdenados(anyInt(), anyInt());
    }

}
