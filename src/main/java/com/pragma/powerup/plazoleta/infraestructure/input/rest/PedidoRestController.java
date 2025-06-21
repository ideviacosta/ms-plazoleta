package com.pragma.powerup.plazoleta.infraestructure.input.rest;

import com.pragma.powerup.plazoleta.application.handler.IPedidoHandler;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoRequestDto;
import com.pragma.powerup.plazoleta.infraestructure.input.rest.dto.PedidoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.RankingEficienciaEmpleadoDto;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.TiempoAtencionPorPedidoDto;
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

import java.util.List;

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

    @PatchMapping("/pedidos/{idPedido}/entregar")
    @Operation(summary = "Marcar pedido como entregado", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido marcado como entregado"),
            @ApiResponse(responseCode = "403", description = "PIN incorrecto o estado no válido")
    })
    public ResponseEntity<Void> entregarPedido(
            @PathVariable Long idPedido,
            @RequestParam int pin,
            HttpServletRequest request
    ) {
        Long idEmpleado = (Long) request.getAttribute(ATTR_ID_USUARIO);
        String rol = (String) request.getAttribute(ATTR_ROL);
        pedidoHandler.marcarPedidoComoEntregado(idPedido, idEmpleado, pin, rol);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/pedidos/{idPedido}/cancelar")
    @Operation(summary = "Cancelar pedido", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido cancelado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado"),
            @ApiResponse(responseCode = "409", description = "Estado del pedido no permite cancelación")
    })
    public ResponseEntity<Void> cancelarPedido(
            @PathVariable Long idPedido,
            HttpServletRequest request
    ) {
        Long idCliente = (Long) request.getAttribute(ATTR_ID_USUARIO);
        String rol = (String) request.getAttribute(ATTR_ROL);
        pedidoHandler.cancelarPedido(idPedido, idCliente, rol);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pedido/{idPedido}/historial")
    @Operation(summary = "Consultar historial de estados de un pedido",
            description = "Permite al cliente ver los cambios de estado de su pedido",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido exitosamente"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado o no pertenece al cliente")
    })
    public ResponseEntity<List<HistorialEstadoResponseDto>> consultarHistorialDePedido(
            @PathVariable Long idPedido,
            HttpServletRequest request
    ) {
        Long idCliente = (Long) request.getAttribute(ATTR_ID_USUARIO);
        String rol = (String) request.getAttribute(ATTR_ROL);

        List<HistorialEstadoResponseDto> historial = pedidoHandler.consultarHistorialDePedido(idPedido, idCliente, rol);
        return ResponseEntity.ok(historial);
    }

    @Operation(summary = "Consultar tiempos de atención por pedido", security = @SecurityRequirement(name = "BearerAuth"))
    @GetMapping("/eficiencia/tiempos")
    public ResponseEntity<List<TiempoAtencionPorPedidoDto>> obtenerTiemposPedidos(HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");
        Long idPropietario = (Long) request.getAttribute("idUsuario");
        return ResponseEntity.ok(pedidoHandler.obtenerTiemposPedidos(idPropietario, rol));
    }

    @Operation(summary = "Consultar ranking de eficiencia por empleado", security = @SecurityRequirement(name = "BearerAuth"))
    @GetMapping("/eficiencia/ranking")
    public ResponseEntity<List<RankingEficienciaEmpleadoDto>> obtenerRanking(HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");
        Long idPropietario = (Long) request.getAttribute("idUsuario");
        return ResponseEntity.ok(pedidoHandler.obtenerRankingEmpleados(idPropietario, rol));
    }


}