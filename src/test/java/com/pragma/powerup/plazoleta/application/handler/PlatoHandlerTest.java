package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PlatoHandlerTest {

    private IPlatoService platoService;
    private PlatoHandler platoHandler;

    @BeforeEach
    void setUp() {
        platoService = mock(IPlatoService.class);
        platoHandler = new PlatoHandler(platoService);
    }

    @Test
    void crearPlato_delegaEnElCasoDeUso() {
        // Arrange
        PlatoRequestDto dto = new PlatoRequestDto();
        dto.setNombre("Pizza");
        dto.setPrecio(15000);
        dto.setDescripcion("Pizza Margarita");
        dto.setUrlImagen("http://img.com/pizza.jpg");
        dto.setIdCategoria(1L);
        dto.setIdRestaurante(1L);

        // Act
        platoHandler.crearPlato(dto, "PROPIETARIO", 2L);

        // Assert
        verify(platoService).crearPlato(any(), eq("PROPIETARIO"), eq(2L));
    }

    @Test
    void testHabilitarPlato() {
        // Arrange
        Long idPlato = 1L;
        boolean nuevoEstado = true;
        String rol = "PROPIETARIO";
        Long idPropietario = 10L;

        // Act
        platoHandler.cambiarEstadoPlato(idPlato, nuevoEstado, rol, idPropietario);

        // Assert
        verify(platoService).cambiarEstadoPlato(idPlato, nuevoEstado, rol, idPropietario);
    }

    @Test
    void testDeshabilitarPlato() {
        // Arrange
        Long idPlato = 2L;
        boolean nuevoEstado = false;
        String rol = "PROPIETARIO";
        Long idPropietario = 20L;

        // Act
        platoHandler.cambiarEstadoPlato(idPlato, nuevoEstado, rol, idPropietario);

        // Assert
        verify(platoService).cambiarEstadoPlato(idPlato, nuevoEstado, rol, idPropietario);
    }

    @Test
    void testLlamadoConRolIncorrectoNoLanzaExcepcionDesdeHandler() {
        // Nota: El handler delega en el service la validación del rol,
        // por lo tanto, desde el handler se llama igual.
        Long idPlato = 3L;
        boolean nuevoEstado = true;
        String rol = "CLIENTE";
        Long idPropietario = 30L;

        platoHandler.cambiarEstadoPlato(idPlato, nuevoEstado, rol, idPropietario);

        verify(platoService).cambiarEstadoPlato(idPlato, nuevoEstado, rol, idPropietario);
    }
}