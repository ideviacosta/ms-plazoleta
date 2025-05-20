package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoRequestDto {

    @NotBlank(message = "El nombre del plato es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Pattern(regexp = "^(http|https)://.*$", message = "La URL debe ser válida")
    private String urlImagen;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    @NotNull(message = "El restaurante es obligatorio")
    private Long idRestaurante;
}
