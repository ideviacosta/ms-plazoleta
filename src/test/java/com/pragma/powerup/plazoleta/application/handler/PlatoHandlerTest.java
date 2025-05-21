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
}