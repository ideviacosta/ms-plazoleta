package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

public class PlatoListadoResponseDto {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String urlImagen;

    public PlatoListadoResponseDto(String nombre, String descripcion, Integer precio, String urlImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImagen = urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}