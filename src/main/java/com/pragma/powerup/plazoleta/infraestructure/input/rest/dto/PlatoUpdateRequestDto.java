package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoUpdateRequestDto {
    private Integer precio;
    private String descripcion;
}