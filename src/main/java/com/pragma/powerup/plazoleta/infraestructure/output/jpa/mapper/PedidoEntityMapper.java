package com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper;

import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PedidoEntity;

public class PedidoEntityMapper {
    public static PedidoEntity toEntity(Pedido model) {
        PedidoEntity entity = new PedidoEntity();
        entity.setId(model.getId());
        entity.setIdCliente(model.getIdCliente());
        entity.setIdRestaurante(model.getIdRestaurante());
        entity.setEstado(model.getEstado());
        entity.setFecha(model.getFecha());
        entity.setIdEmpleadoAsignado(model.getIdEmpleadoAsignado());
        return entity;
    }

    public static Pedido toModel(PedidoEntity entity) {
        return Pedido.builder()
                .id(entity.getId())
                .idCliente(entity.getIdCliente())
                .idRestaurante(entity.getIdRestaurante())
                .estado(entity.getEstado())
                .fecha(entity.getFecha())
                .idEmpleadoAsignado(entity.getIdEmpleadoAsignado())
                .build();
    }
}
