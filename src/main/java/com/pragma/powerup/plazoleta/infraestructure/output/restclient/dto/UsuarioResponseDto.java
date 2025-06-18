package com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDto {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private String celular;
}