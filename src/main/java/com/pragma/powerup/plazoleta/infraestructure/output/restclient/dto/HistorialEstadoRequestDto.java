package com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto;

public class HistorialEstadoRequestDto {
    private Long idPedido;
    private Long idCliente;
    private Long idEmpleado;
    private String estado;

    public HistorialEstadoRequestDto() {}

    public HistorialEstadoRequestDto(Long idPedido, Long idCliente, Long idEmpleado,String estado) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.estado = estado;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Long getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}