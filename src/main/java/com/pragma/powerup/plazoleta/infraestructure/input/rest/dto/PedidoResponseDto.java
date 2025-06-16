package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

import java.util.Date;
import java.util.List;

public class PedidoResponseDto {
    private Long id;
    private Long idCliente;
    private Long idRestaurante;
    private String estado;
    private Date fecha;
    private Long idEmpleadoAsignado;
    private List<PlatoPedidoDto> platos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getIdEmpleadoAsignado() {
        return idEmpleadoAsignado;
    }

    public void setIdEmpleadoAsignado(Long idEmpleadoAsignado) {
        this.idEmpleadoAsignado = idEmpleadoAsignado;
    }

    public List<PlatoPedidoDto> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PlatoPedidoDto> platos) {
        this.platos = platos;
    }
}