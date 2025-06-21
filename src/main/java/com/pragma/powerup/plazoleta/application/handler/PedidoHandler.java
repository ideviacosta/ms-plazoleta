package com.pragma.powerup.plazoleta.application.handler;


import com.pragma.powerup.plazoleta.domain.api.IPedidoService;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.PedidoRequestMapper;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.PedidoResponseMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.RankingEficienciaEmpleadoDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.TiempoAtencionPorPedidoDto;

import java.util.List;


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

    @Override
    public void asignarPedido(Long idPedido, Long idEmpleado, String rolEmpleado){
        pedidoService.asignarPedido(idPedido, idEmpleado, rolEmpleado);
    }

    @Override
    public PaginaRespuesta<PedidoResponseDto> listarPedidosPorEstado(
            EstadoPedido estado, int page, int size, String rol, Long idEmpleado) {

        PaginaRespuesta<Pedido> pagina = pedidoService.listarPedidosPorEstadoYEmpleado(idEmpleado, estado, page, size, rol);

        List<PedidoResponseDto> contenido = pagina.getContenido().stream()
                .map(PedidoResponseMapper::toDto)
                .toList();

        return new PaginaRespuesta<>(
                contenido,
                pagina.getPaginaActual(),
                pagina.getTotalPaginas(),
                pagina.getTotalElementos(),
                pagina.getElementosPorPagina()
        );
    }

    @Override
    public void notificarPedidoListo(Long idPedido, Long idEmpleado, String telefonoDestino,String rol) {
        pedidoService.notificarPedidoListo(idPedido, idEmpleado, telefonoDestino,rol);
    }

    @Override
    public void marcarPedidoComoEntregado(Long idPedido, Long idEmpleado, int pin, String rol) {
        pedidoService.marcarPedidoComoEntregado(idPedido, idEmpleado, pin, rol);
    }

    @Override
    public void cancelarPedido(Long idPedido, Long idCliente, String rol) {
        pedidoService.cancelarPedido(idPedido, idCliente, rol);
    }

    public List<HistorialEstadoResponseDto> consultarHistorialDePedido(Long idPedido, Long idCliente, String rol){
        return pedidoService.consultarHistorialDePedido(idPedido, idCliente, rol);
    }

    public List<TiempoAtencionPorPedidoDto> obtenerTiemposPedidos(Long idPropietario, String rol){
        return pedidoService.obtenerTiemposPorPedido(idPropietario, rol);
    }


    public List<RankingEficienciaEmpleadoDto> obtenerRankingEmpleados(Long idPropietario, String rol){
        return pedidoService.obtenerRankingPorEmpleado(idPropietario, rol);
    }

}