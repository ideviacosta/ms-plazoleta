package com.pragma.powerup.plazoleta.domain.model;

public class PedidoPlato {
    private Long idPlato;
    private Integer cantidad;

    public PedidoPlato(Long idPlato, Integer cantidad) {
        this.idPlato = idPlato;
        this.cantidad = cantidad;
    }

    public Long getIdPlato() { return idPlato; }
    public Integer getCantidad() { return cantidad; }
}
