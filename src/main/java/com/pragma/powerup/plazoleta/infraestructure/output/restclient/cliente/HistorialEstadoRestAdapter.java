package com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente;

import com.pragma.powerup.plazoleta.domain.spi.IHistorialEstadoPersistencePort;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.HistorialEstadoClient;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.RankingEficienciaEmpleadoDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.TiempoAtencionPorPedidoDto;

import java.util.List;

public class HistorialEstadoRestAdapter implements IHistorialEstadoPersistencePort {

    private final HistorialEstadoClient historialEstadoClient;

    public HistorialEstadoRestAdapter(HistorialEstadoClient historialEstadoClient) {
        this.historialEstadoClient = historialEstadoClient;
    }

    @Override
    public void guardarHistorial(HistorialEstadoRequestDto historial) {
        historialEstadoClient.guardarHistorial(historial);
    }

    @Override
    public List<HistorialEstadoResponseDto> obtenerHistorial(Long idPedido, Long idCliente) {
        return historialEstadoClient.obtenerHistorial(idPedido, idCliente);
    }

    @Override
    public List<TiempoAtencionPorPedidoDto> obtenerTiemposPorPedido(Long idRestaurante) {
        return historialEstadoClient.obtenerTiemposPorPedido(idRestaurante);
    }

    @Override
    public List<RankingEficienciaEmpleadoDto> obtenerRankingPorEmpleado(Long idRestaurante) {
        return historialEstadoClient.obtenerRankingPorEmpleado(idRestaurante);
    }
}