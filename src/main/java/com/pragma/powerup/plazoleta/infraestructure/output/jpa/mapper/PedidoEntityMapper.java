package com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper;

import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.model.PedidoPlato;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PedidoEntity;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PedidoPlatoEntity;

import java.util.ArrayList;
import java.util.List;

public class PedidoEntityMapper {
    public static PedidoEntity toEntity(Pedido model) {
        PedidoEntity entity = new PedidoEntity();
        entity.setId(model.getId());
        entity.setIdCliente(model.getIdCliente());
        entity.setIdRestaurante(model.getIdRestaurante());
        entity.setEstado(model.getEstado());
        entity.setFecha(model.getFecha());
        entity.setIdEmpleadoAsignado(model.getIdEmpleadoAsignado());
        entity.setPinSeguridad(model.getPinSeguridad().toString());

        // Mapear platos si vienen presentes
        if (model.getPlatos() != null) {
            List<PedidoPlatoEntity> platos = model.getPlatos().stream().map(p -> {
                PedidoPlatoEntity pe = new PedidoPlatoEntity();
                pe.setIdPlato(p.getIdPlato());
                pe.setCantidad(p.getCantidad());
                pe.setPedido(entity);
                return pe;
            }).toList();
            entity.setPlatos(platos);
        }

        return entity;
    }

    public static Pedido toModel(PedidoEntity entity) {
        List<PedidoPlato> platos = entity.getPlatos() != null
                ? entity.getPlatos().stream()
                .map(p -> new PedidoPlato(p.getIdPlato(), p.getCantidad()))
                .toList()
                : new ArrayList<>();

        return Pedido.builder()
                .id(entity.getId())
                .idCliente(entity.getIdCliente())
                .idRestaurante(entity.getIdRestaurante())
                .estado(entity.getEstado())
                .fecha(entity.getFecha())
                .idEmpleadoAsignado(entity.getIdEmpleadoAsignado())
                .platos(platos)
                .pinSeguridad(Integer.parseInt(entity.getPinSeguridad()))
                .build();
    }

    private PedidoEntityMapper() {
    }
}
