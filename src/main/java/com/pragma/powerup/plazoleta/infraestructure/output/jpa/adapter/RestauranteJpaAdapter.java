package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.RestauranteEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IRestauranteRepository;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.UsuarioRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {

    private final IRestauranteRepository restauranteRepository;
    private final UsuarioRestClient usuarioRestClient;

    @Override
    public void guardarRestaurante(Restaurante restaurante) {
        restauranteRepository.save(RestauranteEntityMapper.toEntity(restaurante));
    }

    @Override
    public boolean propietarioExisteYEsValido(Long idPropietario) {
        try{
            var usuario = usuarioRestClient.obtenerUsuarioPorId(idPropietario);
            return usuario != null && "PROPIETARIO".equalsIgnoreCase(usuario.getRol());
        }catch (RuntimeException e) {
            return false;
        }

    }
}