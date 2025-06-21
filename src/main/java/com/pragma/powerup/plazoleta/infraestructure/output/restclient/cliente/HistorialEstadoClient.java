package com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente;

import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.RankingEficienciaEmpleadoDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.TiempoAtencionPorPedidoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

@Component
public class HistorialEstadoClient {

    private final RestTemplate restTemplate;
    private final String trazabilidadUrl;

    public HistorialEstadoClient(RestTemplate restTemplate,
                                 @Value("${trazabilidad.url}") String trazabilidadUrl) {
        this.restTemplate = restTemplate;
        this.trazabilidadUrl = trazabilidadUrl;
    }

    public void guardarHistorial(HistorialEstadoRequestDto historial) {
        String endpoint = trazabilidadUrl + "/trazabilidad/guardar-historial";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HistorialEstadoRequestDto> request = new HttpEntity<>(historial, headers);
        restTemplate.postForEntity(endpoint, request, Void.class);
    }

    public List<HistorialEstadoResponseDto> obtenerHistorial(Long idPedido, Long idCliente) {
        String endpoint = trazabilidadUrl + "/trazabilidad/historial?idPedido=" + idPedido + "&idCliente=" + idCliente;

        ResponseEntity<HistorialEstadoResponseDto[]> response = restTemplate.getForEntity(
                endpoint,
                HistorialEstadoResponseDto[].class
        );

        HistorialEstadoResponseDto[] historialArray = response.getBody();
        return historialArray != null ? Arrays.asList(historialArray) : List.of();
    }

    public List<TiempoAtencionPorPedidoDto> obtenerTiemposPorPedido(Long idRestaurante) {
        String endpoint = trazabilidadUrl + "/trazabilidad/tiempo-pedidos?idRestaurante=" + idRestaurante;
        ResponseEntity<TiempoAtencionPorPedidoDto[]> response =
                restTemplate.getForEntity(endpoint, TiempoAtencionPorPedidoDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<RankingEficienciaEmpleadoDto> obtenerRankingPorEmpleado(Long idRestaurante) {
        String endpoint = trazabilidadUrl + "/trazabilidad/ranking-empleados?idRestaurante=" + idRestaurante;
        ResponseEntity<RankingEficienciaEmpleadoDto[]> response =
                restTemplate.getForEntity(endpoint, RankingEficienciaEmpleadoDto[].class);
        return Arrays.asList(response.getBody());
    }


}