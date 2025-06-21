package com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto;


public class RankingEficienciaEmpleadoDto {
    private Long idEmpleado;
    private String nombreEmpleado;
    private Double tiempoPromedioAtencionMinutos;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Double getTiempoPromedioAtencionMinutos() {
        return tiempoPromedioAtencionMinutos;
    }

    public void setTiempoPromedioAtencionMinutos(Double tiempoPromedioAtencionMinutos) {
        this.tiempoPromedioAtencionMinutos = tiempoPromedioAtencionMinutos;
    }
}
