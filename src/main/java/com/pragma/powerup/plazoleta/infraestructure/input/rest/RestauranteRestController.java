package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.pragma.powerup.plazoleta.application.handler.IRestauranteHandler;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteListadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.RestauranteRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurante", description = "Operaciones relacionadas con el restaurante")
@RequiredArgsConstructor
public class RestauranteRestController {

    private final IRestauranteHandler restauranteHandler;

    @Operation(summary = "Crear un nuevo restaurante" , security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearRestaurante(@Valid @RequestBody RestauranteRequestDto dto,
                                 HttpServletRequest request) {
        String rolCreador = (String) request.getAttribute("rol");

        restauranteHandler.crearRestaurante(dto, rolCreador);
    }

    @GetMapping("/restaurantes")
    @Operation(summary = "Listar restaurantes", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado")
    })
    public PaginaRespuesta<RestauranteListadoResponseDto> listarRestaurantes(
            @RequestParam(name = "pagina", defaultValue = "0") int page,
            @RequestParam(name = "tamanio", defaultValue = "10") int size,
            HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");
        return restauranteHandler.listarRestaurantes(page, size, rol);
    }

}
