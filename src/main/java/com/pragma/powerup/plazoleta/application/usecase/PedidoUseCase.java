package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.api.IPedidoService;
import com.pragma.powerup.plazoleta.domain.exception.EstadoPedidoInvalidoException;
import com.pragma.powerup.plazoleta.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.plazoleta.domain.exception.PedidoYaAsignadoException;
import com.pragma.powerup.plazoleta.domain.exception.PinInvalidoException;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IPedidoPersistencePort;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.NotificacionSmsClient;
import com.pragma.powerup.plazoleta.util.PinUtil;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

import static com.pragma.powerup.plazoleta.util.MensajesError.*;
import static com.pragma.powerup.plazoleta.util.RolValidator.validarRol;
import static com.pragma.powerup.plazoleta.util.Roles.*;

public class PedidoUseCase implements IPedidoService {

    private final IPedidoPersistencePort persistencePort;
    private final IEmpleadoRestaurantePersistencePort empleadoRestaurantePort;
    private final NotificacionSmsClient notificacionSmsClient;

    public PedidoUseCase(
            IPedidoPersistencePort persistencePort,
            IEmpleadoRestaurantePersistencePort empleadoRestaurantePort,
            NotificacionSmsClient notificacionSmsClient
    ) {
        this.persistencePort = persistencePort;
        this.empleadoRestaurantePort = empleadoRestaurantePort;
        this.notificacionSmsClient = notificacionSmsClient;
    }

    @Override
    public void realizarPedido(Pedido pedido, String rol, Long idCliente) {
        validarRol(rol
                , CLIENTE);
        if (persistencePort.clienteTienePedidoEnProceso(idCliente)) {
            throw new PedidoEnProcesoException(PEDIDO_EN_PROCESO);
        }
        Pedido pedidoAguardar = Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(pedido.getIdRestaurante())
                .estado(EstadoPedido.PENDIENTE)
                .fecha(Date.from(Instant.now()))
                .platos(pedido.getPlatos())
                .pinSeguridad(Integer.parseInt(PinUtil.generarPinAleatorio()))
                .build();
        persistencePort.guardarPedido(pedidoAguardar);
    }

    @Override
    public void asignarPedido(Long idPedido, Long idEmpleado, String rol) {
        validarRol(rol, EMPLEADO);
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
    public Pedido obtenerPedidoPorId(Long idPedido, String rol, Long idEmpleado) {
        validarRol(rol, EMPLEADO);
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

    @Override
    public void notificarPedidoListo(Long idPedido, Long idEmpleado, String telefonoDestino, String rol) {
        validarRol(rol, EMPLEADO);
        Pedido pedido = persistencePort.obtenerPedidoPorId(idPedido);
        Long restauranteEmpleado = empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado);
        if (!pedido.getIdRestaurante().equals(restauranteEmpleado)) {
            throw new EstadoPedidoInvalidoException(PEDIDO_NO_PERTENECE_A_RESTAURANTE);
        }

        pedido.setEstado(EstadoPedido.LISTO);
        persistencePort.guardarPedido(pedido);
        notificacionSmsClient.enviarSms(telefonoDestino,
                                PEDIDO_LISTO_CODIGO_DE_ENTREGA + pedido.getPinSeguridad());
    }


    @Override
    public void marcarPedidoComoEntregado(Long idPedido, Long idEmpleado, int pinIngresado, String rol) {
        validarRol(rol, EMPLEADO);
        Pedido pedido = persistencePort.obtenerPedidoPorId(idPedido);
        Long restauranteEmpleado = empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado);

        if (!pedido.getIdRestaurante().equals(restauranteEmpleado)) {
            throw new EstadoPedidoInvalidoException(PEDIDO_NO_PERTENECE_A_RESTAURANTE);
        }

        if (!pedido.getEstado().equals(EstadoPedido.LISTO)) {
            throw new EstadoPedidoInvalidoException(SOLO_SE_PUEDE_MARCAR_ENTREGADO_SI_LISTO);
        }

        if (!pedido.getPinSeguridad().equals(pinIngresado)) {
            throw new PinInvalidoException(PIN_INCORRECTO);
        }

        pedido.setEstado(EstadoPedido.ENTREGADO);
        persistencePort.guardarPedido(pedido);
    }

    @Override
    public void cancelarPedido(Long idPedido, Long idCliente, String rol) {
        validarRol(rol, CLIENTE);

        Pedido pedido = persistencePort.obtenerPedidoPorId(idPedido);

        if (!pedido.getIdCliente().equals(idCliente)) {
            throw new EstadoPedidoInvalidoException(PEDIDO_NO_PERTENECE_A_CLIENTE);
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new EstadoPedidoInvalidoException(PEDIDO_EN_PREPARACION_NO_CANCELABLE);
        }

        pedido.setEstado(EstadoPedido.CANCELADO);
        persistencePort.guardarPedido(pedido);
    }

}