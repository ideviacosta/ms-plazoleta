package com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "platos")
@Getter
@Setter
public class PlatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer precio;

    private String descripcion;

    private String urlImagen;

    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    private Boolean activo;

    public static final String CAMPO_NOMBRE = "nombre";
}