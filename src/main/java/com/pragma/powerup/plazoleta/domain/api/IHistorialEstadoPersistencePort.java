package com.pragma.powerup.plazoleta.domain.api;

import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.RankingEficienciaEmpleadoDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.TiempoAtencionPorPedidoDto;

import java.util.List;

public interface IHistorialEstadoPersistencePort {

    void guardarHistorial(HistorialEstadoRequestDto historial);

    List<HistorialEstadoResponseDto> obtenerHistorial(Long idPedido, Long idCliente);

    List<TiempoAtencionPorPedidoDto> obtenerTiemposPorPedido(Long idRestaurante);

    List<RankingEficienciaEmpleadoDto> obtenerRankingPorEmpleado(Long idRestaurante);
}