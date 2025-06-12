package com.pragma.powerup.plazoleta.application.handler;

import com.pragma.powerup.plazoleta.domain.api.IRestauranteService;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Restaurante;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteListadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.mapper.RestauranteRequestMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteHandler implements IRestauranteHandler {

    private final IRestauranteService restauranteService;

    public RestauranteHandler(IRestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Override
    public void crearRestaurante(RestauranteRequestDto dto, String rolCreador) {
        Restaurante restaurante = RestauranteRequestMapper.toModel(dto);
        restauranteService.crearRestaurante(restaurante, rolCreador);
    }

    @Override
    public PaginaRespuesta<RestauranteListadoResponseDto> listarRestaurantes(int page, int size, String rol) {
        PaginaRespuesta<Restaurante> pagina = restauranteService.listarRestaurantes(page, size, rol);
        List<RestauranteListadoResponseDto> contenido = pagina.getContenido().stream()
                .map(r -> new RestauranteListadoResponseDto(r.getNombre(), r.getUrlLogo()))
                .toList();

        return new PaginaRespuesta<>(
                contenido,
                pagina.getPaginaActual(),
                pagina.getTotalPaginas(),
                pagina.getTotalElementos(),
                pagina.getElementosPorPagina()
        );
    }
}