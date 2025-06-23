package com.pragma.powerup.plazoleta.domain.api;

import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.RankingEficienciaEmpleadoDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.TiempoAtencionPorPedidoDto;

import java.util.List;

public interface IPedidoService {
    void realizarPedido(Pedido pedido, String rolCliente, Long idCliente);
    void asignarPedido(Long idPedido, Long idEmpleado, String rolEmpleado);
    Pedido obtenerPedidoPorId(Long idPedido, String rolEmpleado, Long idEmpleado);
    PaginaRespuesta<Pedido> listarPedidosPorEstadoYEmpleado(Long idEmpleado, EstadoPedido estado, int page, int size, String rol);
    void notificarPedidoListo(Long idPedido, Long idEmpleado, String telefonoDestino, String rolCliente);
    void marcarPedidoComoEntregado(Long idPedido, Long idEmpleado, int pinIngresado, String rolEmpleado);
    void cancelarPedido(Long idPedido, Long idCliente, String rol);
    List<HistorialEstadoResponseDto> consultarHistorialDePedido(Long idPedido, Long idCliente, String rol);
    List<TiempoAtencionPorPedidoDto> obtenerTiemposPorPedido(Long idPropietario, Long idRestaurante, String rol);
    List<RankingEficienciaEmpleadoDto> obtenerRankingPorEmpleado(Long idPropietario, Long idRestaurante,String rol);
}