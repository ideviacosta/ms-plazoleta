package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.exception.EmpleadoNoAsociadoRestauranteException;
import com.pragma.powerup.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.EmpleadoRestauranteEntity;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IEmpleadoRestauranteRepository;
import lombok.RequiredArgsConstructor;

import static com.pragma.powerup.plazoleta.util.MensajesError.*;

@RequiredArgsConstructor
public class EmpleadoRestauranteJpaAdapter implements IEmpleadoRestaurantePersistencePort {

    private final IEmpleadoRestauranteRepository repository;

    @Override
    public Long obtenerIdRestaurantePorEmpleado(Long idEmpleado) {
        return repository.findByIdEmpleado(idEmpleado)
                .map(EmpleadoRestauranteEntity::getIdRestaurante)
                .orElseThrow(() -> new EmpleadoNoAsociadoRestauranteException(EMPLEADO_NO_ASOCIADO_RESTAURANTE));
    }
}