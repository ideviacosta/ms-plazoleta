package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoResponseDto;

public interface IPedidoHandler {
    void realizarPedido(PedidoRequestDto pedidoDto, Long idCliente, String rolCliente);
    void asignarPedido(Long idPedido, Long idEmpleado, String rolEmpleado);
    PaginaRespuesta<PedidoResponseDto> listarPedidosPorEstado(EstadoPedido estado, int page, int size, String rol, Long idEmpleado);
    void notificarPedidoListo(Long idPedido, Long idEmpleado, String telefonoDestino, String rolCliente);
}