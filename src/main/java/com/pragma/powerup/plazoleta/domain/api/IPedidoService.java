package com.pragma.powerup.plazoleta.domain.api;

import com.pragma.powerup.plazoleta.domain.model.Pedido;

public interface IPedidoService {
    void realizarPedido(Pedido pedido, String rolCliente, Long idCliente);
}