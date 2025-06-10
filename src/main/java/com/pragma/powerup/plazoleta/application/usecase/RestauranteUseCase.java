package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.exception.PropietarioInvalidoException;
import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;

import java.util.List;

import static com.pragma.powerup.plazoleta.util.MensajesError.*;
import static com.pragma.powerup.plazoleta.util.RolValidator.*;
import static com.pragma.powerup.plazoleta.util.Roles.*;


public class RestauranteUseCase implements IRestauranteService {

    private final IRestaurantePersistencePort persistencePort;

    public RestauranteUseCase(IRestaurantePersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public void crearRestaurante(Restaurante restaurante, String rolCreador) {
        validarRol(rolCreador, ADMINISTRADOR);

        if (!persistencePort.propietarioExisteYEsValido(restaurante.getIdPropietario())) {
            throw new PropietarioInvalidoException(PROPIETARIO_INVALIDO);
        }

        persistencePort.guardarRestaurante(restaurante);
    }

    @Override
    public List<Restaurante> listarRestaurantes(int page, int size, String rol) {
        validarRol(rol, CLIENTE);
        return persistencePort.obtenerRestaurantesOrdenados(page, size).getContent();
    }

}
