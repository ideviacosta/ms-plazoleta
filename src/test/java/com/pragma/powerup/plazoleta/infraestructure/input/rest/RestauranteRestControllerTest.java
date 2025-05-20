package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.plazoleta.application.handler.IRestauranteHandler;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestauranteRestController.class)
class RestauranteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRestauranteHandler restauranteHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearRestaurante_retorna201_siRequestEsValidoYRolCorrecto() throws Exception {
        RestauranteRequestDto dto = new RestauranteRequestDto();
        dto.setNombre("Restaurante X");
        dto.setNit("123456");
        dto.setDireccion("Calle 123");
        dto.setTelefono("+573001112233");
        dto.setUrlLogo("https://logo.png");
        dto.setIdPropietario(1L);

        doNothing().when(restauranteHandler).crearRestaurante(any(), eq("ADMINISTRADOR"));

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Rol", "ADMINISTRADOR")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void crearRestaurante_retorna400_siRequestInvalido() throws Exception {
        RestauranteRequestDto dto = new RestauranteRequestDto(); // sin datos

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Rol", "ADMINISTRADOR")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}