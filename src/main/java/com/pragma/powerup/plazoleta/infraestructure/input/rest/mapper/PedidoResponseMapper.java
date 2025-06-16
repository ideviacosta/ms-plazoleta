package com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper;

import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoPedidoDto;

public class PedidoResponseMapper {
    public static PedidoResponseDto toDto(Pedido pedido) {
        PedidoResponseDto dto = new PedidoResponseDto();
        dto.setId(pedido.getId());
        dto.setIdCliente(pedido.getIdCliente());
        dto.setIdRestaurante(pedido.getIdRestaurante());
        dto.setEstado(pedido.getEstado().name());
        dto.setFecha(pedido.getFecha());
        dto.setIdEmpleadoAsignado(pedido.getIdEmpleadoAsignado());
        dto.setPlatos(pedido.getPlatos().stream()
                .map(plato -> new PlatoPedidoDto(plato.getIdPlato(), plato.getCantidad()))
                .toList());
        return dto;
    }
}