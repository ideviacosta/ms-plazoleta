package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.RestauranteRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RestauranteHandlerTest {

    private IRestauranteService restauranteService;
    private RestauranteHandler handler;

    @BeforeEach
    void setUp() {
        restauranteService = mock(IRestauranteService.class);
        handler = new RestauranteHandler(restauranteService);
    }

    @Test
    void crearRestaurante_delegaEnService() {
        RestauranteRequestDto dto = new RestauranteRequestDto();
        dto.setNombre("Sushi House");
        dto.setNit("555666777");
        dto.setDireccion("Carrera 45 #23-12");
        dto.setTelefono("+573001112233");
        dto.setUrlLogo("https://example.com/logo.png");
        dto.setIdPropietario(1L);

        handler.crearRestaurante(dto, "ADMINISTRADOR");

        Restaurante esperado = RestauranteRequestMapper.toModel(dto);
        verify(restauranteService).crearRestaurante(refEq(esperado), eq("ADMINISTRADOR"));
    }
}