package com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado_restaurante")
public class EmpleadoRestauranteEntity {

    @Id
    @Column(name = "id_empleado")
    private Long idEmpleado;

    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

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