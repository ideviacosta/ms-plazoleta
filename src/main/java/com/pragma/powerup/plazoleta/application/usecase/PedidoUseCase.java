package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.api.IPedidoService;
import com.pragma.powerup.plazoleta.domain.exception.EstadoPedidoInvalidoException;
import com.pragma.powerup.plazoleta.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.plazoleta.domain.exception.PedidoYaAsignadoException;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IPedidoPersistencePort;

import java.time.Instant;
import java.util.Date;

import static com.pragma.powerup.plazoleta.util.MensajesError.*;
import static com.pragma.powerup.plazoleta.util.RolValidator.validarRol;
import static com.pragma.powerup.plazoleta.util.Roles.*;

public class PedidoUseCase implements IPedidoService {

    private final IPedidoPersistencePort persistencePort;
    private final IEmpleadoRestaurantePersistencePort empleadoRestaurantePort;

    public PedidoUseCase(IPedidoPersistencePort persistencePort, IEmpleadoRestaurantePersistencePort empleadoRestaurantePort) {
        this.persistencePort = persistencePort;
        this.empleadoRestaurantePort = empleadoRestaurantePort;
    }

    @Override
    public void realizarPedido(Pedido pedido, String rolCliente, Long idCliente) {
        validarRol(rolCliente, CLIENTE);
        if (persistencePort.clienteTienePedidoEnProceso(idCliente)) {
            throw new PedidoEnProcesoException(PEDIDO_EN_PROCESO);
        }
        Pedido pedidoAguardar = Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(pedido.getIdRestaurante())
                .estado(EstadoPedido.PENDIENTE)
                .fecha(Date.from(Instant.now()))
                .platos(pedido.getPlatos())
                .build();
        persistencePort.guardarPedido(pedidoAguardar);
    }

    @Override
    public void asignarPedido(Long idPedido, Long idEmpleado, String rolEmpleado) {
        validarRol(rolEmpleado, EMPLEADO);
        Pedido pedido = persistencePort.obtenerPedidoPorId(idPedido);
        if (!pedido.getEstado().equals(EstadoPedido.PENDIENTE)) {
            throw new EstadoPedidoInvalidoException(SOLO_SE_PUEDE_ASIGNAR_PEDIDOS_EN_PENDIENTE);
        }
        Long idRestauranteEmpleado = empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado);
        if (!pedido.getIdRestaurante().equals(idRestauranteEmpleado)) {
            throw new EstadoPedidoInvalidoException(PEDIDO_NO_PERTENECE_A_RESTAURANTE);
        }
        pedido.setIdEmpleadoAsignado(idEmpleado);
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        persistencePort.asignarPedido(idPedido, idEmpleado);
    }


    @Override
    public Pedido obtenerPedidoPorId(Long idPedido, String rolEmpleado, Long idEmpleado) {
        validarRol(rolEmpleado, EMPLEADO);
        Pedido pedido = persistencePort.obtenerPedidoPorId(idPedido);
        if (pedido.getIdEmpleadoAsignado() != null) {
            throw new PedidoYaAsignadoException(PEDIDO_YA_ASIGNADO);
        }
        return pedido;
    }

    @Override
    public PaginaRespuesta<Pedido> listarPedidosPorEstadoYEmpleado(
            Long idEmpleado, EstadoPedido estado, int page, int size, String rol) {
        validarRol(rol, EMPLEADO);
        // Obtener restaurante al que pertenece el empleado
        Long idRestaurante = empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado);
        // Filtrar pedidos por restaurante y estado
        return persistencePort.listarPedidosPorEstadoYEmpleado(idRestaurante, idEmpleado, estado, page, size);
    }



}