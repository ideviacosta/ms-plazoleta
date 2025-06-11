package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;

public interface IPedidoHandler {
    void realizarPedido(PedidoRequestDto pedidoDto, Long idCliente, String rolCliente);
}