package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteUseCase implements IRestauranteService {

    private final IRestaurantePersistencePort persistencePort;

    @Override
    public void crearRestaurante(Restaurante restaurante, String rolCreador) {
        if (!"ADMINISTRADOR".equalsIgnoreCase(rolCreador.trim())) {
            throw new RuntimeException("Solo un administrador puede crear restaurantes");
        }

        if (!persistencePort.propietarioExisteYEsValido(restaurante.getIdPropietario())) {
            throw new RuntimeException("Propietario inválido: no existe o no tiene rol PROPIETARIO");
        }

        persistencePort.guardarRestaurante(restaurante);
    }
}