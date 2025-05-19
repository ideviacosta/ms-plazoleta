package com.pragma.powerup.plazoleta.infraestructure.config;

import com.pragma.powerup.plazoleta.application.usecase.RestauranteUseCase;
import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantePersistencePort restaurantePersistencePort;

    @Bean
    public IRestauranteService restauranteService() {
        return new RestauranteUseCase(restaurantePersistencePort);
    }
}