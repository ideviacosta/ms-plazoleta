package com.pragma.powerup.plazoleta.infraestructure.config;

import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.application.handler.PlatoHandler;
import com.pragma.powerup.plazoleta.application.usecase.PlatoUseCase;
import com.pragma.powerup.plazoleta.application.usecase.RestauranteUseCase;
import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;
import com.pragma.powerup.plazoleta.domain.util.JwtUtil;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter.PlatoJpaAdapter;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter.RestauranteJpaAdapter;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IPlatoRepository;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IRestauranteRepository;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.UsuarioRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestauranteJpaAdapter restauranteJpaAdapter(IRestauranteRepository restauranteRepository,
                                                       UsuarioRestClient usuarioRestClient) {
        return new RestauranteJpaAdapter(restauranteRepository, usuarioRestClient);
    }

    @Bean
    public IRestauranteService restauranteService(IRestaurantePersistencePort restaurantePersistencePort) {
        return new RestauranteUseCase(restaurantePersistencePort);
    }

    @Bean
    public IPlatoPersistencePort platoPersistencePort(IPlatoRepository platoRepository) {
        return new PlatoJpaAdapter(platoRepository);
    }

    @Bean
    public IPlatoService platoService(IPlatoPersistencePort persistencePort,
                                      IRestauranteValidationPort validationPort) {
        return new PlatoUseCase(persistencePort, validationPort);
    }

    @Bean
    public IPlatoHandler platoHandler(IPlatoService platoService) {
        return new PlatoHandler(platoService);
    }

    @Bean
    public JwtUtil jwtUtil(@Value("${jwt.secret}") String secret) {
        return new JwtUtil(secret);
    }
}
