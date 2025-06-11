package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.pragma.powerup.plazoleta.application.handler.IPlatoHandler;
import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoEstadoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoListadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PlatoUpdateRequestDto;
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
@RequestMapping("/platos")
@Tag(name = "Platos", description = "Operaciones relacionadas con los platos")
@RequiredArgsConstructor
public class PlatoRestController {

    private final IPlatoHandler platoHandler;

    @Operation(summary = "Crear un nuevo plato" , security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crearPlato(@Valid @RequestBody PlatoRequestDto dto,
                           HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");
        Long idPropietario = (Long) request.getAttribute("idUsuario");

        platoHandler.crearPlato(dto, rol, idPropietario);
    }

    @Operation(summary = "Modificar un plato existente" , security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plato modificado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PutMapping("/{idPlato}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modificarPlato(@PathVariable Long idPlato,
                               @RequestBody PlatoUpdateRequestDto dto,
                               HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");
        Long idPropietario = (Long) request.getAttribute("idUsuario");

        platoHandler.modificarPlato(idPlato, dto, rol, idPropietario);
    }

    @Operation(summary = "Habilitar o deshabilitar un plato", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plato modificado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PutMapping("/{idPlato}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarEstadoPlato(@PathVariable Long idPlato,
                                   @RequestBody PlatoEstadoRequestDto dto,
                                   HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");
        Long idPropietario = (Long) request.getAttribute("idUsuario");

        platoHandler.cambiarEstadoPlato(idPlato, dto.isHabilitar(), rol, idPropietario);
    }

    @Operation(summary = "Listar platos de restaurante", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado")
    })
    @GetMapping("/restaurantes/{idRestaurante}/platos")
    public List<PlatoListadoResponseDto> listarPlatosPorRestaurante(
            @PathVariable Long idRestaurante,
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        String rol = (String) request.getAttribute("rol");
        return platoHandler.listarPlatosPorRestaurante(idRestaurante, idCategoria, page, size, rol);
    }



}
