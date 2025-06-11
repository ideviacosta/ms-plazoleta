package com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper;

import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PedidoEntity;

public class PedidoEntityMapper {
    public static PedidoEntity toEntity(Pedido pedido) {
        PedidoEntity entity = new PedidoEntity();
        entity.setId(pedido.getId());
        entity.setIdCliente(pedido.getIdCliente());
        entity.setIdRestaurante(pedido.getIdRestaurante());
        entity.setEstado(pedido.getEstado());
        entity.setFecha(pedido.getFecha());
        return entity;
    }
}
