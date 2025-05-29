package com.pragma.powerup.plazoleta.domain.api;


import com.pragma.powerup.plazoleta.domain.model.Plato;

public interface IPlatoService {
    void crearPlato(Plato plato, String rol, Long idPropietario);
    void modificarPlato(Long idPlato, Integer nuevoPrecio, String nuevaDescripcion, String rol, Long idPropietario);
    void cambiarEstadoPlato(Long idPlato, boolean habilitar, String rol, Long idPropietario);

}