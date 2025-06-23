package com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto;

import java.util.Date;

public class TiempoAtencionPorPedidoDto {
    private Long idPedido;
    private Long idEmpleado;
    private Long idCliente;
    private Long tiempoEnMinutos;

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getTiempoEnMinutos() {
        return tiempoEnMinutos;
    }

    public void setTiempoEnMinutos(Long tiempoEnMinutos) {
        this.tiempoEnMinutos = tiempoEnMinutos;
    }
}