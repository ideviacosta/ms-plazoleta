package com.pragma.powerup.plazoleta.domain.model;

import java.util.Date;
import java.util.List;

public class Pedido {
    private Long id;
    private Long idCliente;
    private Long idRestaurante;
    private EstadoPedido estado;
    private Date fecha;
    private List<PedidoPlato> platos;

    private Pedido(Builder builder) {
        this.id = builder.id;
        this.idCliente = builder.idCliente;
        this.idRestaurante = builder.idRestaurante;
        this.estado = builder.estado;
        this.fecha = builder.fecha;
        this.platos = builder.platos;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long idCliente;
        private Long idRestaurante;
        private EstadoPedido estado;
        private Date fecha;
        private List<PedidoPlato> platos;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder idCliente(Long idCliente) {
            this.idCliente = idCliente;
            return this;
        }

        public Builder idRestaurante(Long idRestaurante) {
            this.idRestaurante = idRestaurante;
            return this;
        }

        public Builder estado(EstadoPedido estado) {
            this.estado = estado;
            return this;
        }

        public Builder fecha(Date fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder platos(List<PedidoPlato> platos) {
            this.platos = platos;
            return this;
        }

        public Pedido build() {
            return new Pedido(this);
        }
    }

    // Getters
    public Long getId() { return id; }
    public Long getIdCliente() { return idCliente; }
    public Long getIdRestaurante() { return idRestaurante; }
    public EstadoPedido getEstado() { return estado; }
    public Date getFecha() { return fecha; }
    public List<PedidoPlato> getPlatos() { return platos; }
}
