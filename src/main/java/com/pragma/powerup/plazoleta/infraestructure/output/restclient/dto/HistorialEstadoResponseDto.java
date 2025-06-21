package com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto;

import java.util.Date;

public class HistorialEstadoResponseDto {
    private String estado;
    private Date fechaCambio;

    public HistorialEstadoResponseDto() {
    }

    public HistorialEstadoResponseDto(String estado, Date fechaCambio) {
        this.estado = estado;
        this.fechaCambio = fechaCambio;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }
}
