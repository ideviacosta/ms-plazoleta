package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.exception.PropietarioInvalidoException;
import com.pragma.powerup.plazoleta.domain.exception.ValidacionCampoException;
import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pragma.powerup.plazoleta.util.MensajesError.PROPIETARIO_NO_DUENIO_PLATO;
import static com.pragma.powerup.plazoleta.util.Roles.PROPIETARIO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatoUseCaseTest {

    private IPlatoPersistencePort persistencePort;
    private IRestauranteValidationPort restauranteValidationPort;
    private PlatoUseCase platoUseCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IPlatoPersistencePort.class);
        restauranteValidationPort = mock(IRestauranteValidationPort.class);
        platoUseCase = new PlatoUseCase(persistencePort, restauranteValidationPort);
    }

    private Plato buildPlatoValido() {
        return Plato.builder()
                .nombre("Pizza")
                .precio(10000)
                .descripcion("Rica")
                .urlImagen("img.jpg")
                .idCategoria(1L)
                .idRestaurante(2L)
                .activo(true)
                .build();
    }

    @Test
    void crearPlato_ok() {
        Plato plato = buildPlatoValido();
        when(restauranteValidationPort.esPropietarioDelRestaurante(10L, 2L)).thenReturn(true);

        assertDoesNotThrow(() -> platoUseCase.crearPlato(plato, "PROPIETARIO", 10L));
        verify(persistencePort).guardarPlato(plato);
    }

    @Test
    void crearPlato_lanzaExcepcionSiRolInvalido() {
        Plato plato = buildPlatoValido();
        Exception ex = assertThrows(RuntimeException.class, () -> platoUseCase.crearPlato(plato, "ADMIN", 1L));
        assertTrue(ex.getMessage().contains("Se requiere rol: PROPIETARIO"));
    }

    @Test
    void crearPlato_lanzaExcepcionSiNoEsPropietario() {
        Plato plato = buildPlatoValido();
        when(restauranteValidationPort.esPropietarioDelRestaurante(99L, 2L)).thenReturn(false);

        assertThrows(PropietarioInvalidoException.class, () -> platoUseCase.crearPlato(plato, "PROPIETARIO", 99L));
    }

    @Test
    void crearPlato_lanzaExcepcionSiPrecioInvalido() {
        Plato plato = Plato.builder().nombre("Arroz").precio(0).descripcion("Descripción")
                .urlImagen("url").idCategoria(1L).idRestaurante(1L).activo(true).build();
        when(restauranteValidationPort.esPropietarioDelRestaurante(eq(1L), eq(1L))).thenReturn(true);

        assertThrows(ValidacionCampoException.class, () -> platoUseCase.crearPlato(plato, "PROPIETARIO", 1L));
    }

    @Test
    void crearPlato_lanzaExcepcionSiCamposObligatoriosFaltan() {
        Plato plato = Plato.builder().nombre("").precio(20000).descripcion("").urlImagen("")
                .idCategoria(1L).idRestaurante(1L).activo(true).build();
        when(restauranteValidationPort.esPropietarioDelRestaurante(eq(1L), eq(1L))).thenReturn(true);

        assertThrows(ValidacionCampoException.class, () -> platoUseCase.crearPlato(plato, "PROPIETARIO", 1L));
    }

    @Test
    void crearPlato_lanzaExcepcionSiCategoriaNula() {
        Plato plato = Plato.builder().nombre("Arroz").precio(15000).descripcion("Rico").urlImagen("url")
                .idCategoria(null).idRestaurante(1L).activo(true).build();
        when(restauranteValidationPort.esPropietarioDelRestaurante(eq(1L), eq(1L))).thenReturn(true);

        assertThrows(ValidacionCampoException.class, () -> platoUseCase.crearPlato(plato, "PROPIETARIO", 1L));
    }

    @Test
    void modificarPlato_ok() {
        when(restauranteValidationPort.esPropietarioDelPlato(5L, 1L)).thenReturn(true);

        assertDoesNotThrow(() -> platoUseCase.modificarPlato(5L, 15000, "Nueva desc", "PROPIETARIO", 1L));
        verify(persistencePort).actualizarPlato(5L, 15000, "Nueva desc");
    }

    @Test
    void modificarPlato_lanzaExcepcionSiRolIncorrecto() {
        Exception ex = assertThrows(RuntimeException.class, () -> platoUseCase.modificarPlato(1L, 10000, "desc", "ADMIN", 1L));
        assertTrue(ex.getMessage().contains("Se requiere rol: PROPIETARIO"));
    }

    @Test
    void modificarPlato_lanzaExcepcionSiDatosInvalidos() {
        assertThrows(ValidacionCampoException.class, () -> platoUseCase.modificarPlato(1L, -500, "", "PROPIETARIO", 1L));
    }

    @Test
    void modificarPlato_lanzaExcepcionSiNoEsPropietarioDelPlato() {
        when(restauranteValidationPort.esPropietarioDelPlato(1L, 2L)).thenReturn(false);
        assertThrows(PropietarioInvalidoException.class, () -> platoUseCase.modificarPlato(1L, 12000, "Desc", "PROPIETARIO", 2L));
    }

    @Test
    void cambiarEstadoPlato_DeberiaActualizarEstado_SiEsPropietarioValido() {
        Long idPlato = 1L;
        boolean habilitar = true;
        Long idPropietario = 10L;
        when(restauranteValidationPort.esPropietarioDelPlato(idPlato, idPropietario)).thenReturn(true);

        platoUseCase.cambiarEstadoPlato(idPlato, habilitar, PROPIETARIO, idPropietario);

        verify(persistencePort).cambiarEstadoPlato(idPlato, habilitar);
    }

    @Test
    void cambiarEstadoPlato_DeberiaLanzarExcepcion_SiNoEsPropietarioDelPlato() {
        Long idPlato = 1L;
        boolean habilitar = false;
        Long idPropietario = 20L;
        when(restauranteValidationPort.esPropietarioDelPlato(idPlato, idPropietario)).thenReturn(false);

        PropietarioInvalidoException exception = assertThrows(
                PropietarioInvalidoException.class,
                () -> platoUseCase.cambiarEstadoPlato(idPlato, habilitar, PROPIETARIO, idPropietario)
        );

        assertEquals(PROPIETARIO_NO_DUENIO_PLATO, exception.getMessage());
        verify(persistencePort, never()).cambiarEstadoPlato(anyLong(), anyBoolean());
    }

    @Test
    void cambiarEstadoPlato_DeberiaLanzarExcepcion_SiRolNoEsPropietario() {
        Long idPlato = 1L;
        boolean habilitar = true;
        String rol = "EMPLEADO";
        Long idPropietario = 10L;

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> platoUseCase.cambiarEstadoPlato(idPlato, habilitar, rol, idPropietario)
        );

        assertTrue(exception.getMessage().contains("Se requiere rol: PROPIETARIO"));
        verifyNoInteractions(restauranteValidationPort);
        verifyNoInteractions(persistencePort);
    }

    @Test
    void listarPlatosPorRestauranteYCategoria_ok() {
        // Arrange
        Long idRestaurante = 1L;
        Long idCategoria = 2L;
        int page = 0;
        int size = 5;
        String rol = "CLIENTE";

        List<Plato> mockLista = List.of(
                Plato.builder().id(1L).nombre("Arroz").build(),
                Plato.builder().id(2L).nombre("Pollo").build()
        );

        when(persistencePort.listarPlatosPorRestaurante(idRestaurante, idCategoria, page, size))
                .thenReturn(mockLista);

        // Act
        List<Plato> resultado = platoUseCase.listarPlatosPorRestaurante(idRestaurante, idCategoria, page, size, rol);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(persistencePort).listarPlatosPorRestaurante(idRestaurante, idCategoria, page, size);
    }

    @Test
    void listarPlatosPorRestaurante_sinCategoria_ok() {
        Long idRestaurante = 1L;
        Long idCategoria = null;
        int page = 1;
        int size = 3;
        String rol = "CLIENTE";

        List<Plato> mockLista = List.of(Plato.builder().id(1L).nombre("Ensalada").build());

        when(persistencePort.listarPlatosPorRestaurante(idRestaurante, null, page, size)).thenReturn(mockLista);

        List<Plato> resultado = platoUseCase.listarPlatosPorRestaurante(idRestaurante, idCategoria, page, size, rol);

        assertEquals(1, resultado.size());
        verify(persistencePort).listarPlatosPorRestaurante(idRestaurante, null, page, size);
    }

    @Test
    void listarPlatosPorRestaurante_rolInvalido_lanzaExcepcion() {
        Long idRestaurante = 1L;
        Long idCategoria = null;
        int page = 0;
        int size = 5;
        String rol = "PROPIETARIO";

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> platoUseCase.listarPlatosPorRestaurante(idRestaurante, idCategoria, page, size, rol));

        assertTrue(ex.getMessage().contains("Se requiere rol: CLIENTE"));
        verifyNoInteractions(persistencePort);
    }
}