package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.RankingEficienciaEmpleadoDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.TiempoAtencionPorPedidoDto;

import java.util.List;

public interface IPedidoHandler {
    void realizarPedido(PedidoRequestDto pedidoDto, Long idCliente, String rolCliente);
    void asignarPedido(Long idPedido, Long idEmpleado, String rolEmpleado);
    PaginaRespuesta<PedidoResponseDto> listarPedidosPorEstado(EstadoPedido estado, int page, int size, String rol, Long idEmpleado);
    void notificarPedidoListo(Long idPedido, Long idEmpleado, String telefonoDestino, String rolCliente);
    void marcarPedidoComoEntregado(Long idPedido, Long idEmpleado, int pin, String rol);
    void cancelarPedido(Long idPedido, Long idCliente, String rol);
    List<HistorialEstadoResponseDto> consultarHistorialDePedido(Long idPedido, Long idCliente, String rol);
    List<TiempoAtencionPorPedidoDto> obtenerTiemposPedidos(Long idPropietario, String rol);
    List<RankingEficienciaEmpleadoDto> obtenerRankingEmpleados(Long idPropietario, String rol);
}