package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

import java.util.List;

public class PedidoRequestDto {
    private Long idRestaurante;
    private List<PlatoCantidadDto> platos;

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public List<PlatoCantidadDto> getPlatos() { return platos; }
    public void setPlatos(List<PlatoCantidadDto> platos) { this.platos = platos; }
}