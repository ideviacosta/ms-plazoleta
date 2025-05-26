package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.config.DisableJwtFilterConfig;
import com.pragma.powerup.plazoleta.config.TestFilterConfig;
import com.pragma.powerup.plazoleta.config.TestSecurityConfig;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlatoRestController.class)
@Import({TestSecurityConfig.class, DisableJwtFilterConfig.class})
class PlatoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPlatoHandler platoHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearPlato_devuelveCreatedSiEsValido() throws Exception {
        PlatoRequestDto dto = new PlatoRequestDto();
        dto.setNombre("Hamburguesa");
        dto.setPrecio(12000);
        dto.setDescripcion("Doble carne");
        dto.setUrlImagen("https://img.com/img.jpg");
        dto.setIdCategoria(1L);
        dto.setIdRestaurante(1L);

        doNothing().when(platoHandler).crearPlato(any(), eq("PROPIETARIO"), eq(1L));

        mockMvc.perform(post("/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .requestAttr("usuarioRol", "PROPIETARIO")
                        .requestAttr("usuarioId", 1L))
                .andExpect(status().isCreated());
    }

    @Test
    void modificarPlato_devuelveNoContentSiEsValido() throws Exception {
        PlatoUpdateRequestDto dto = new PlatoUpdateRequestDto();
        dto.setPrecio(15000);
        dto.setDescripcion("Con queso");

        doNothing().when(platoHandler).modificarPlato(eq(5L), any(), eq("PROPIETARIO"), eq(1L));

        mockMvc.perform(put("/platos/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .requestAttr("usuarioRol", "PROPIETARIO")
                        .requestAttr("usuarioId", 1L))
                .andExpect(status().isNoContent());
    }
}
