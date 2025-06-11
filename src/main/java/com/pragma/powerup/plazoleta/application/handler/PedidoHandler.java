package com.pragma.powerup.plazoleta.application.handler;


import com.pragma.powerup.plazoleta.domain.api.IPedidoService;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.PedidoRequestMapper;


public class PedidoHandler implements IPedidoHandler {

    private final IPedidoService pedidoService;

    public PedidoHandler(IPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Override
    public void realizarPedido(PedidoRequestDto pedidoDto, Long idCliente, String rolCliente) {
        Pedido pedido = PedidoRequestMapper.toModel(pedidoDto, idCliente);
        pedidoService.realizarPedido(pedido, rolCliente, idCliente);
    }
}