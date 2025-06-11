package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

public class PlatoCantidadDto {
    private Long idPlato;
    private Integer cantidad;

    public Long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Long idPlato) {
        this.idPlato = idPlato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}