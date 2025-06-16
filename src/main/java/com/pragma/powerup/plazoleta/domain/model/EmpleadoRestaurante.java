package com.pragma.powerup.plazoleta.domain.model;

public class EmpleadoRestaurante {
    private Long idEmpleado;
    private Long idRestaurante;

    public EmpleadoRestaurante(Long idEmpleado, Long idRestaurante) {
        this.idEmpleado = idEmpleado;
        this.idRestaurante = idRestaurante;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }
}