package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

public class RestauranteListadoResponseDto {
    private String nombre;
    private String urlLogo;

    public RestauranteListadoResponseDto() {}

    public RestauranteListadoResponseDto(String nombre, String urlLogo) {
        this.nombre = nombre;
        this.urlLogo = urlLogo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlLogo() {
        return urlLogo;
    }}
