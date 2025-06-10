package com.pragma.powerup.plazoleta.domain.spi;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import org.springframework.data.domain.Page;

public interface IRestaurantePersistencePort {
    void guardarRestaurante(Restaurante restaurante);
    boolean propietarioExisteYEsValido(Long idPropietario);
    Page<Restaurante> obtenerRestaurantesOrdenados(int page, int size);

}