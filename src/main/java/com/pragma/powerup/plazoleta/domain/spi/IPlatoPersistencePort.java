package com.pragma.powerup.plazoleta.domain.spi;

import com.pragma.powerup.plazoleta.domain.model.Plato;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
    void actualizarPlato(Long idPlato, Integer nuevoPrecio, String nuevaDescripcion);

}