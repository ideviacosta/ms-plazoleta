package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.plazoleta.application.handler.IRestauranteHandler;
import com.pragma.powerup.plazoleta.config.DisableJwtFilterConfig;
import com.pragma.powerup.plazoleta.config.TestFilterConfig;
import com.pragma.powerup.plazoleta.config.TestSecurityConfig;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestauranteRestController.class)
@Import({TestSecurityConfig.class, DisableJwtFilterConfig.class})
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
        dto.setNombre("La Fonda");
        dto.setNit("123456789");
        dto.setDireccion("Calle 10 # 20-30");
        dto.setTelefono("+573001234567");
        dto.setUrlLogo("https://img.com/logo.png");
        dto.setIdPropietario(1L);

        doNothing().when(restauranteHandler).crearRestaurante(any(), eq("ADMINISTRADOR"));

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .requestAttr("usuarioRol", "ADMINISTRADOR"))
                .andExpect(status().isCreated());
    }

    @Test
    void crearRestaurante_retorna400_siRequestInvalido() throws Exception {
        RestauranteRequestDto dto = new RestauranteRequestDto(); // Campos vacíos

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .requestAttr("usuarioRol", "ADMINISTRADOR"))
                .andExpect(status().isBadRequest());
    }
}
