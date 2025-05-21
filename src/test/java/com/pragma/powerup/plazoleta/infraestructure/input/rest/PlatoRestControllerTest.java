package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@WebMvcTest(PlatoRestController.class)
class PlatoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPlatoHandler platoHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearPlato_devuelveCreatedSiEsValido() throws Exception {
        // Arrange
        PlatoRequestDto dto = new PlatoRequestDto();
        dto.setNombre("Hamburguesa");
        dto.setPrecio(12000);
        dto.setDescripcion("Doble carne");
        dto.setUrlImagen("https://img.com/img.jpg");
        dto.setIdCategoria(1L);
        dto.setIdRestaurante(1L);

        doNothing().when(platoHandler).crearPlato(any(), eq("PROPIETARIO"), eq(1L));

        // Act & Assert
        mockMvc.perform(post("/platos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Rol", "PROPIETARIO")
                        .header("Id", 1L)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void modificarPlato_devuelveNoContentSiEsValido() throws Exception {
        Long idPlato = 1L;
        PlatoUpdateRequestDto updateDto = new PlatoUpdateRequestDto();
        updateDto.setPrecio(18000);
        updateDto.setDescripcion("Nueva descripción");

        doNothing().when(platoHandler).modificarPlato(eq(idPlato), any(PlatoUpdateRequestDto.class), eq("PROPIETARIO"), eq(1L));

        mockMvc.perform(put("/platos/{id}", idPlato)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Rol", "PROPIETARIO")
                        .header("Id", 1L)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNoContent());
    }
}