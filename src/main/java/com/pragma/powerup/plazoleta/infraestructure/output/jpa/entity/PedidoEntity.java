package com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity;

import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCliente;
    private Long idRestaurante;

    @Enumerated(EnumType.STRING)
    private com.pragma.powerup.plazoleta.domain.model.EstadoPedido estado;

    private Date fecha;

    private Long idEmpleadoAsignado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoPlatoEntity> platos;

    @Column(name = "pin_seguridad", nullable = false)
    private String pinSeguridad;



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

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
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

    public List<PedidoPlatoEntity> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PedidoPlatoEntity> platos) {
        this.platos = platos;
    }

    public String getPinSeguridad() {return pinSeguridad; }

    public void setPinSeguridad(String pinSeguridad) {this.pinSeguridad = pinSeguridad; }
}
