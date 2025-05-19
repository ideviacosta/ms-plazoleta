package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequestDto {

    @Pattern(regexp = "^(?!\\d+$)[\\w\\s\\d]+$", message = "El nombre no puede ser solo números")
    private String nombre;

    @Pattern(regexp = "\\d+", message = "El NIT debe ser numérico")
    private String nit;

    @NotBlank
    private String direccion;

    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Teléfono inválido")
    private String telefono;

    @NotBlank
    private String urlLogo;

    @NotNull
    private Long idPropietario;
}