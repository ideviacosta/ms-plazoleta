package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.RestauranteEntity;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.RestauranteEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IRestauranteRepository;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.UsuarioRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort, IRestauranteValidationPort
 {

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
     @Override
     public boolean esPropietarioDelRestaurante(Long idPropietario, Long idRestaurante) {
         try {
             var usuario = usuarioRestClient.obtenerUsuarioPorId(idPropietario);
             if (usuario == null || !"PROPIETARIO".equalsIgnoreCase(usuario.getRol())) {
                 return false;
             }

             return restauranteRepository.existsByIdAndIdPropietario(idRestaurante, idPropietario);
         } catch (RuntimeException e) {
             return false;
         }
     }

     @Override
     public boolean esPropietarioDelPlato(Long idPlato, Long idPropietario) {
         return restauranteRepository.existsByIdAndIdPropietario(idPlato, idPropietario);
     }

     @Override
     public PaginaRespuesta<Restaurante> obtenerRestaurantesOrdenados(int page, int size) {
         Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());

         Page<RestauranteEntity> pageResult = restauranteRepository.findAllByOrderByNombreAsc(pageable);

         List<Restaurante> contenido = pageResult.getContent()
                 .stream()
                 .map(RestauranteEntityMapper::toModel)
                 .toList();

         return new PaginaRespuesta<>(
                 contenido,
                 pageResult.getNumber(),              // página actual
                 pageResult.getSize(),                // elementos por página
                 pageResult.getTotalElements(),       // total de elementos
                 pageResult.getTotalPages()           // total de páginas
         );
     }




 }