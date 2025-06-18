package com.pragma.powerup.plazoleta.domain.api;

import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Pedido;

public interface IPedidoService {
    void realizarPedido(Pedido pedido, String rolCliente, Long idCliente);
    void asignarPedido(Long idPedido, Long idEmpleado, String rolEmpleado);
    Pedido obtenerPedidoPorId(Long idPedido, String rolEmpleado, Long idEmpleado);
    PaginaRespuesta<Pedido> listarPedidosPorEstadoYEmpleado(Long idEmpleado, EstadoPedido estado, int page, int size, String rol);
    void notificarPedidoListo(Long idPedido, Long idEmpleado, String telefonoDestino, String rolCliente);
    void marcarPedidoComoEntregado(Long idPedido, Long idEmpleado, int pinIngresado, String rolEmpleado);
    void cancelarPedido(Long idPedido, Long idCliente, String rol);
}