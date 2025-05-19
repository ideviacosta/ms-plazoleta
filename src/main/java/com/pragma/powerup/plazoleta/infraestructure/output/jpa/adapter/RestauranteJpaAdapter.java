package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.RestauranteEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {

    private final IRestauranteRepository restauranteRepository;

    @Override
    public void guardarRestaurante(Restaurante restaurante) {
        restauranteRepository.save(RestauranteEntityMapper.toEntity(restaurante));
    }

    @Override
    public boolean propietarioExisteYEsValido(Long idPropietario) {
        // (true) hasta conectar con ms-usuarios
        return true;
    }
}