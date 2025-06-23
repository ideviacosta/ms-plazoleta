package com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto;


public class RankingEficienciaEmpleadoDto {
    private Long idEmpleado;
    private Double promedioMinutos;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Double getPromedioMinutos() {
        return promedioMinutos;
    }

    public void setPromedioMinutos(Double promedioMinutos) {
        this.promedioMinutos = promedioMinutos;
    }
}
