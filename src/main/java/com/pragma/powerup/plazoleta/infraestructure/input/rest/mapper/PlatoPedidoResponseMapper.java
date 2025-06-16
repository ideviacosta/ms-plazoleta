package com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper;

import com.pragma.powerup.plazoleta.domain.model.PedidoPlato;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoPedidoDto;

public class PlatoPedidoResponseMapper {

    private PlatoPedidoResponseMapper() {
    }

    public static PlatoPedidoDto toDto(PedidoPlato platoPedido) {
        return new PlatoPedidoDto(
                platoPedido.getIdPlato(),
                platoPedido.getCantidad()
        );
    }
}