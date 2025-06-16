package com.pragma.powerup.plazoleta.domain.spi;

import com.pragma.powerup.plazoleta.domain.model.Pedido;

public interface IPedidoPersistencePort {
    boolean clienteTienePedidoEnProceso(Long idCliente);
    void guardarPedido(Pedido pedido);
    void asignarPedido(Long idPedido, Long idEmpleado);
    Pedido obtenerPedidoPorId(Long idPedido);

}