package com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper;

import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.model.PedidoPlato;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoRequestMapper {
    public static Pedido toModel(PedidoRequestDto dto, Long idCliente) {
        List<PedidoPlato> platos = dto.getPlatos().stream()
                .map(p -> new PedidoPlato(p.getIdPlato(), p.getCantidad()))
                .collect(Collectors.toList());

        return Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(dto.getIdRestaurante())
                .platos(platos)
                .build();
    }

    private PedidoRequestMapper() {
    }
}
