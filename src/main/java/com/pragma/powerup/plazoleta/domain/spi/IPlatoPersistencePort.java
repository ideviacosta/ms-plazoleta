package com.pragma.powerup.plazoleta.domain.spi;

import com.pragma.powerup.plazoleta.domain.model.Plato;

import java.util.List;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
    void actualizarPlato(Long idPlato, Integer nuevoPrecio, String nuevaDescripcion);
    void cambiarEstadoPlato(Long idPlato, boolean habilitar);
    List<Plato> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int page, int size);

}