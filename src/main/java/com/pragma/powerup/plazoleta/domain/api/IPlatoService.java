package com.pragma.powerup.plazoleta.domain.api;


import com.pragma.powerup.plazoleta.domain.model.Plato;

import java.util.List;

public interface IPlatoService {
    void crearPlato(Plato plato, String rol, Long idPropietario);
    void modificarPlato(Long idPlato, Integer nuevoPrecio, String nuevaDescripcion, String rol, Long idPropietario);
    void cambiarEstadoPlato(Long idPlato, boolean habilitar, String rol, Long idPropietario);
    List<Plato> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int page, int size, String rol);
}