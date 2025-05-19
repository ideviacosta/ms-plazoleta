package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.pragma.powerup.plazoleta.application.handler.IRestauranteHandler;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteRestController {

    private final IRestauranteHandler restauranteHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearRestaurante(@Valid @RequestBody RestauranteRequestDto dto,
                                 @RequestHeader("Rol") String rolCreador) {
        restauranteHandler.crearRestaurante(dto, rolCreador);
    }
}