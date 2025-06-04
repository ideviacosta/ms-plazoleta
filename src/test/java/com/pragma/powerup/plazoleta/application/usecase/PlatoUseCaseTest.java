package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.exception.PropietarioInvalidoException;
import com.pragma.powerup.plazoleta.domain.exception.ValidacionCampoException;
import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void crearPlato_ok() {
        Plato plato = new Plato(null, "Pizza",  10000, "Rica","img.jpg", 1L, 2L, true);
        when(restauranteValidationPort.esPropietarioDelRestaurante(10L, 2L)).thenReturn(true);

        assertDoesNotThrow(() -> platoUseCase.crearPlato(plato, "PROPIETARIO", 10L));
        verify(persistencePort).guardarPlato(plato);
    }

    @Test
    void crearPlato_lanzaExcepcionSiRolInvalido() {
        Plato plato = new Plato();
        Exception ex = assertThrows(RuntimeException.class, () -> platoUseCase.crearPlato(plato, "ADMIN", 1L));
        assertTrue(ex.getMessage().contains("Se requiere rol: PROPIETARIO"));
    }

    @Test
    void crearPlato_lanzaExcepcionSiNoEsPropietario() {
        Plato plato = new Plato(null, "Pizza",  10000, "Rica","img.jpg", 1L, 2L, true);
        when(restauranteValidationPort.esPropietarioDelRestaurante(99L, 2L)).thenReturn(false);

        assertThrows(PropietarioInvalidoException.class, () -> platoUseCase.crearPlato(plato, "PROPIETARIO", 99L));
    }

    @Test
    void crearPlato_lanzaExcepcionSiPrecioInvalido() {
        Plato plato = new Plato(1L, "Arroz", 0, "Descripción", "url", 1L, 1L, true);
        when(restauranteValidationPort.esPropietarioDelRestaurante(eq(1L), eq(1L))).thenReturn(true);
        assertThrows(ValidacionCampoException.class, () ->
                platoUseCase.crearPlato(plato, "PROPIETARIO", 1L)
        );  }

    @Test
    void crearPlato_lanzaExcepcionSiCamposObligatoriosFaltan() {
        Plato plato = new Plato(1L, "", 20000, "", "", 1L, 1L, true);
        when(restauranteValidationPort.esPropietarioDelRestaurante(eq(1L), eq(1L))).thenReturn(true);

        assertThrows(ValidacionCampoException.class, () ->
                platoUseCase.crearPlato(plato, "PROPIETARIO", 1L)
        );   }

    @Test
    void crearPlato_lanzaExcepcionSiCategoriaNula() {
        Plato plato = new Plato(1L, "Arroz",15000 , "Rico", "url", null, 1L, true);
        when(restauranteValidationPort.esPropietarioDelRestaurante(eq(1L), eq(1L))).thenReturn(true);

        assertThrows(ValidacionCampoException.class, () ->
                platoUseCase.crearPlato(plato, "PROPIETARIO", 1L)
        );   }

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
        // Arrange
        Long idPlato = 1L;
        boolean habilitar = true;
        String rol = PROPIETARIO;
        Long idPropietario = 10L;

        when(restauranteValidationPort.esPropietarioDelPlato(idPlato, idPropietario)).thenReturn(true);

        // Act
        platoUseCase.cambiarEstadoPlato(idPlato, habilitar, rol, idPropietario);

        // Assert
        verify(persistencePort).cambiarEstadoPlato(idPlato, habilitar);
    }

    @Test
    void cambiarEstadoPlato_DeberiaLanzarExcepcion_SiNoEsPropietarioDelPlato() {
        // Arrange
        Long idPlato = 1L;
        boolean habilitar = false;
        String rol = PROPIETARIO;
        Long idPropietario = 20L;

        when(restauranteValidationPort.esPropietarioDelPlato(idPlato, idPropietario)).thenReturn(false);

        // Act & Assert
        PropietarioInvalidoException exception = assertThrows(
                PropietarioInvalidoException.class,
                () -> platoUseCase.cambiarEstadoPlato(idPlato, habilitar, rol, idPropietario)
        );

        assertEquals(PROPIETARIO_NO_DUENIO_PLATO, exception.getMessage());
        verify(persistencePort, never()).cambiarEstadoPlato(anyLong(), anyBoolean());
    }

    @Test
    void cambiarEstadoPlato_DeberiaLanzarExcepcion_SiRolNoEsPropietario() {
        // Arrange
        Long idPlato = 1L;
        boolean habilitar = true;
        String rol = "EMPLEADO"; // rol no válido
        Long idPropietario = 10L;

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> platoUseCase.cambiarEstadoPlato(idPlato, habilitar, rol, idPropietario)
        );

        assertTrue(exception.getMessage().contains("Se requiere rol: PROPIETARIO"));
        verifyNoInteractions(restauranteValidationPort);
        verifyNoInteractions(persistencePort);
    }

}