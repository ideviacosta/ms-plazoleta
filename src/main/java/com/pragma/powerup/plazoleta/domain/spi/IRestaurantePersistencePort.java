package com.pragma.powerup.plazoleta.domain.spi;

import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Restaurante;

public interface IRestaurantePersistencePort {
    void guardarRestaurante(Restaurante restaurante);
    boolean propietarioExisteYEsValido(Long idPropietario);
    PaginaRespuesta<Restaurante> obtenerRestaurantesOrdenados(int page, int size);

}