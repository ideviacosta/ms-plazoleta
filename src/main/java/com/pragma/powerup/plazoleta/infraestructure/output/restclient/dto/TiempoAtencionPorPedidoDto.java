package com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto;

import java.util.Date;

public class TiempoAtencionPorPedidoDto {
    private Long idPedido;
    private Date fechaInicio;
    private Date fechaFin;
    private Long duracionEnMinutos;

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(Long duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }
}