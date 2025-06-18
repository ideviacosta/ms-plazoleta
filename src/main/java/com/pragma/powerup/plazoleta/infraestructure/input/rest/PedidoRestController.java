package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.pragma.powerup.plazoleta.application.handler.IPedidoHandler;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos de los platos")
public class PedidoRestController {

    private final IPedidoHandler pedidoHandler;
    private static final String ATTR_ID_USUARIO = "idUsuario";
    private static final String ATTR_ROL = "rol";

    public PedidoRestController(IPedidoHandler pedidoHandler) {
        this.pedidoHandler = pedidoHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Realizar pedido", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado")
    })
    public void realizarPedido(@Valid @RequestBody PedidoRequestDto pedidoDto, HttpServletRequest request) {
        String rol = (String) request.getAttribute(ATTR_ROL);
        Long idCliente = (Long) request.getAttribute(ATTR_ID_USUARIO);

        pedidoHandler.realizarPedido(pedidoDto, idCliente, rol);
    }

    @PutMapping("/{idPedido}/asignar")
    @Operation(summary = "Asignarse a un pedido", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Pedido asignado"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado o estado inválido")
    })
    public void asignarPedido(
            @PathVariable Long idPedido,
            HttpServletRequest request) {
        String rol = (String) request.getAttribute(ATTR_ROL);
        Long idEmpleado = (Long) request.getAttribute(ATTR_ID_USUARIO);
        pedidoHandler.asignarPedido(idPedido, idEmpleado, rol);
    }

    @GetMapping
    @Operation(summary = "Listar pedidos por estado", security = @SecurityRequirement(name = "BearerAuth"))
    public PaginaRespuesta<PedidoResponseDto> listarPedidosPorEstado(
            @RequestParam EstadoPedido estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        String rol = (String) request.getAttribute(ATTR_ROL);
        Long idEmpleado = (Long) request.getAttribute(ATTR_ID_USUARIO);
        return pedidoHandler.listarPedidosPorEstado(estado, page, size, rol, idEmpleado);
    }

    @PostMapping("/pedidos/{idPedido}/notificar-listo")
    @Operation(summary = "Notificar que el pedido está listo", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Notificación enviada"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado o estado inválido")
    })
    public ResponseEntity<Void> notificarPedidoListo(@PathVariable Long idPedido, @RequestParam String telefonoDestino, HttpServletRequest request) {
        Long idEmpleado = (Long) request.getAttribute(ATTR_ID_USUARIO);
        String rol = (String) request.getAttribute(ATTR_ROL);
        pedidoHandler.notificarPedidoListo(idPedido, idEmpleado, telefonoDestino, rol);
        return ResponseEntity.ok().build();
    }

}