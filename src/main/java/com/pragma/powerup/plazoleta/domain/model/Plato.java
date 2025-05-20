package com.pragma.powerup.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plato {
    private Long id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private Long idCategoria;
    private Long idRestaurante;
    private Boolean activo;
}