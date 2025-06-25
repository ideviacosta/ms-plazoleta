package com.pragma.powerup.plazoleta.infraestructure.config;

import com.pragma.powerup.plazoleta.application.handler.IPedidoHandler;
import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.application.handler.PedidoHandler;
import com.pragma.powerup.plazoleta.application.handler.PlatoHandler;
import com.pragma.powerup.plazoleta.application.usecase.PedidoUseCase;
import com.pragma.powerup.plazoleta.application.usecase.PlatoUseCase;
import com.pragma.powerup.plazoleta.application.usecase.RestauranteUseCase;
import com.pragma.powerup.plazoleta.domain.api.IHistorialEstadoPersistencePort;
import com.pragma.powerup.plazoleta.domain.api.IPedidoService;
import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.spi.*;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter.EmpleadoRestauranteJpaAdapter;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter.PedidoJpaAdapter;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IEmpleadoRestauranteRepository;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IPedidoRepository;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.HistorialEstadoClient;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.HistorialEstadoRestAdapter;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.NotificacionSmsClient;
import com.pragma.powerup.plazoleta.util.JwtUtil;
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

    // ---------- Adaptadores de salida ----------
    @Bean
    public RestauranteJpaAdapter restauranteJpaAdapter(IRestauranteRepository restauranteRepository,
                                                       UsuarioRestClient usuarioRestClient) {
        return new RestauranteJpaAdapter(restauranteRepository, usuarioRestClient);
    }

    @Bean
    public IHistorialEstadoPersistencePort historialEstadoPersistencePort(HistorialEstadoClient historialEstadoClient) {
        return new HistorialEstadoRestAdapter(historialEstadoClient);
    }

    @Bean
    public PlatoJpaAdapter platoJpaAdapter(IPlatoRepository platoRepository) {
        return new PlatoJpaAdapter(platoRepository);
    }

    @Bean
    public EmpleadoRestauranteJpaAdapter empleadoRestauranteJpaAdapter(IEmpleadoRestauranteRepository repository) {
        return new EmpleadoRestauranteJpaAdapter(repository);
    }

    @Bean
    public PedidoJpaAdapter pedidoJpaAdapter(IPedidoRepository pedidoRepository) {
        return new PedidoJpaAdapter(pedidoRepository);
    }

    // ---------- Casos de uso (servicios de dominio) ----------
    @Bean
    public IRestauranteService restauranteService(IRestaurantePersistencePort restaurantePersistencePort) {
        return new RestauranteUseCase(restaurantePersistencePort);
    }

    @Bean
    public IPlatoService platoService(IPlatoPersistencePort persistencePort,
                                      IRestauranteValidationPort validationPort) {
        return new PlatoUseCase(persistencePort, validationPort);
    }

    @Bean
    public IPedidoService pedidoService(IPedidoPersistencePort pedidoPersistencePort, IEmpleadoRestaurantePersistencePort empleadoRestaurantePort, NotificacionSmsClient notificacionSmsClient,  IHistorialEstadoPersistencePort historialEstadoPersistencePort,IRestauranteValidationPort restauranteValidationPort) {
        return new PedidoUseCase(pedidoPersistencePort, empleadoRestaurantePort , notificacionSmsClient, historialEstadoPersistencePort,restauranteValidationPort);
    }

    // ---------- Handlers ----------
    @Bean
    public IPlatoHandler platoHandler(IPlatoService platoService) {
        return new PlatoHandler(platoService);
    }

    @Bean
    public IPedidoHandler pedidoHandler(IPedidoService pedidoService) {
        return new PedidoHandler(pedidoService);
    }

    // ---------- Utilidades ----------
    @Bean
    public JwtUtil jwtUtil(@Value("${jwt.secret}") String secret) {
        return new JwtUtil(secret);
    }
}
