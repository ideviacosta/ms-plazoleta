package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
@RequiredArgsConstructor
public class PlatoRestController {

    private final IPlatoHandler platoHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearPlato(@Valid @RequestBody PlatoRequestDto dto,
                           @RequestHeader("Rol") String rol,
                           @RequestHeader("Id") Long idPropietario) {
        platoHandler.crearPlato(dto, rol, idPropietario);
    }

    @PutMapping("/{idPlato}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modificarPlato(@PathVariable Long idPlato,
                               @RequestBody PlatoUpdateRequestDto dto,
                               @RequestHeader("Rol") String rol,
                               @RequestHeader("Id") Long idPropietario) {
        System.out.println("path variable " + idPlato);
        platoHandler.modificarPlato(idPlato, dto, rol, idPropietario);
    }

}