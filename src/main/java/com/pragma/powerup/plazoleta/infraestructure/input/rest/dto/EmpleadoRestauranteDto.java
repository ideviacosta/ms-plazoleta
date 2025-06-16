package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

public class EmpleadoRestauranteDto {
    private Long idEmpleado;
    private Long idRestaurante;

    public EmpleadoRestauranteDto() {
    }

    public EmpleadoRestauranteDto(Long idEmpleado, Long idRestaurante) {
        this.idEmpleado = idEmpleado;
        this.idRestaurante = idRestaurante;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
}