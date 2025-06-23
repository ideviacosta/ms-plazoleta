package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.exception.EstadoPedidoInvalidoException;
import com.pragma.powerup.plazoleta.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.plazoleta.domain.exception.PedidoYaAsignadoException;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.model.PedidoPlato;
import com.pragma.powerup.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IPedidoPersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.HistorialEstadoClient;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente.NotificacionSmsClient;
import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.HistorialEstadoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import com.pragma.powerup.plazoleta.domain.exception.RolNoAutorizadoException;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;

import static com.pragma.powerup.plazoleta.util.MensajesError.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoUseCaseTest {

    private IPedidoPersistencePort persistencePort;
    private IEmpleadoRestaurantePersistencePort empleadoRestaurantePort;
    private NotificacionSmsClient notificacionSmsClient;
    private PedidoUseCase pedidoUseCase;
    private HistorialEstadoClient historialEstadoClient;
    private IRestauranteValidationPort restauranteValidationPort;


    @BeforeEach
    void setUp() {
        persistencePort = mock(IPedidoPersistencePort.class);
        empleadoRestaurantePort=mock(IEmpleadoRestaurantePersistencePort.class);
        notificacionSmsClient = mock(NotificacionSmsClient.class);
        historialEstadoClient = mock(HistorialEstadoClient.class);
        restauranteValidationPort =mock(IRestauranteValidationPort.class);
        pedidoUseCase = new PedidoUseCase(persistencePort, empleadoRestaurantePort, notificacionSmsClient, historialEstadoClient, restauranteValidationPort);
    }


    @Test
    void realizarPedido_DeberiaLanzarExcepcion_SiClienteYaTienePedidoEnProceso() {
        // Arrange
        Long idCliente = 2L;
        String rol = "CLIENTE";
        Pedido pedido = Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(20L)
                .platos(List.of(new PedidoPlato(2L, 1)))
                .build();

        when(persistencePort.clienteTienePedidoEnProceso(idCliente)).thenReturn(true);

        // Act & Assert
        PedidoEnProcesoException exception = assertThrows(
                PedidoEnProcesoException.class,
                () -> pedidoUseCase.realizarPedido(pedido, rol, idCliente)
        );

        assertEquals(PEDIDO_EN_PROCESO, exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
    }

    @Test
    void realizarPedido_DeberiaLanzarExcepcion_SiRolNoEsCliente() {
        // Arrange
        Long idCliente = 4L;
        String rol = "EMPLEADO"; // no autorizado
        Pedido pedido = Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(40L)
                .platos(List.of(new PedidoPlato(4L, 1)))
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pedidoUseCase.realizarPedido(pedido, rol, idCliente)
        );

        assertTrue(exception.getMessage().contains("Se requiere rol: CLIENTE"));
        verifyNoInteractions(persistencePort);
    }

    @Test
    void noDebePermitirNuevoPedido_siClienteYaTienePedidoEnProceso() {
        Pedido pedido = Pedido.builder()
                .idRestaurante(1L)
                .platos(List.of(new PedidoPlato(2L, 1)))
                .build();

        when(persistencePort.clienteTienePedidoEnProceso(9L)).thenReturn(true);

        PedidoEnProcesoException exception = assertThrows(
                PedidoEnProcesoException.class,
                () -> pedidoUseCase.realizarPedido(pedido, "CLIENTE", 9L)
        );

        assertEquals("Pedido en proceso", exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
    }

    @Test
    void asignarPedido_exitoso() {
        Pedido pedido = Pedido.builder()
                .id(1L)
                .estado(EstadoPedido.PENDIENTE)
                .idRestaurante(1L)
                .build();

        when(persistencePort.obtenerPedidoPorId(1L)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(5L)).thenReturn(1L);

        pedidoUseCase.asignarPedido(1L, 5L, "EMPLEADO");

        verify(persistencePort).asignarPedido(1L, 5L);
    }

    @Test
    void asignarPedido_deberiaLanzarExcepcion_siEstadoNoEsPendiente() {
        // Arrange
        Long idPedido = 100L;
        Long idEmpleado = 50L;
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .estado(EstadoPedido.EN_PREPARACION) // Estado no válido
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);

        // Act & Assert
        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.asignarPedido(idPedido, idEmpleado, rol)
        );

        assertEquals(SOLO_SE_PUEDE_ASIGNAR_PEDIDOS_EN_PENDIENTE, exception.getMessage());
        verify(persistencePort, never()).asignarPedido(anyLong(), anyLong());
    }

    @Test
    void asignarPedido_deberiaLanzarExcepcion_siPedidoNoPerteneceARestauranteDelEmpleado() {
        // Arrange
        Long idPedido = 99L;
        Long idEmpleado = 8L;
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(1L) // Restaurante del pedido
                .estado(EstadoPedido.PENDIENTE)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(2L); // Restaurante distinto

        // Act & Assert
        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.asignarPedido(idPedido, idEmpleado, rol)
        );

        assertEquals("El pedido no pertenece al restaurante del empleado", exception.getMessage());
        verify(persistencePort, never()).asignarPedido(any(), any());
    }


    @Test
    void listarPedidosPorEstadoYEmpleado_deberiaRetornarPaginaCorrecta_siRolEsEmpleado() {
        // Arrange
        Long idEmpleado = 10L;
        Long idRestaurante = 99L;
        EstadoPedido estado = EstadoPedido.EN_PREPARACION;
        int page = 0;
        int size = 5;
        String rol = "EMPLEADO";

        Pedido pedido1 = Pedido.builder().id(1L).estado(estado).idEmpleadoAsignado(idEmpleado).build();
        Pedido pedido2 = Pedido.builder().id(2L).estado(estado).idEmpleadoAsignado(idEmpleado).build();
        List<Pedido> pedidos = List.of(pedido1, pedido2);
        PaginaRespuesta<Pedido> paginaMock = new PaginaRespuesta<>(pedidos, page, 1, 2, size);

        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(idRestaurante);
        when(persistencePort.listarPedidosPorEstadoYEmpleado(idRestaurante, idEmpleado, estado, page, size))
                .thenReturn(paginaMock);

        // Act
        PaginaRespuesta<Pedido> resultado = pedidoUseCase.listarPedidosPorEstadoYEmpleado(idEmpleado, estado, page, size, rol);

        // Assert
        assertEquals(2, resultado.getTotalElementos());
        assertEquals(1L, resultado.getContenido().get(0).getId());
        assertEquals(2L, resultado.getContenido().get(1).getId());
        verify(empleadoRestaurantePort).obtenerIdRestaurantePorEmpleado(idEmpleado);
        verify(persistencePort).listarPedidosPorEstadoYEmpleado(idRestaurante, idEmpleado, estado, page, size);
    }

    @Test
    void listarPedidosPorEstadoYEmpleado_deberiaLanzarExcepcion_siRolNoEsEmpleado() {
        // Arrange
        Long idEmpleado = 5L;
        EstadoPedido estado = EstadoPedido.PENDIENTE;
        int page = 0;
        int size = 5;
        String rolNoValido = "CLIENTE";

        // Act & Assert
        RolNoAutorizadoException exception = assertThrows(
                RolNoAutorizadoException.class,
                () -> pedidoUseCase.listarPedidosPorEstadoYEmpleado(idEmpleado, estado, page, size, rolNoValido)
        );

        assertTrue(exception.getMessage().contains("Se requiere rol: EMPLEADO"));
        verifyNoInteractions(persistencePort);
        verifyNoInteractions(empleadoRestaurantePort);
    }

    @Test
    void obtenerPedidoPorId_deberiaRetornarPedido_siNoTieneEmpleadoAsignado() {
        Pedido pedido = Pedido.builder()
                .id(10L)
                .idEmpleadoAsignado(null)
                .build();

        when(persistencePort.obtenerPedidoPorId(10L)).thenReturn(pedido);

        Pedido resultado = pedidoUseCase.obtenerPedidoPorId(10L, "EMPLEADO", 8L);

        assertEquals(10L, resultado.getId());
        verify(persistencePort).obtenerPedidoPorId(10L);
    }

    @Test
    void obtenerPedidoPorId_deberiaLanzarExcepcion_siPedidoYaAsignado() {
        Pedido pedido = Pedido.builder()
                .id(11L)
                .idEmpleadoAsignado(5L)
                .build();

        when(persistencePort.obtenerPedidoPorId(11L)).thenReturn(pedido);

        PedidoYaAsignadoException exception = assertThrows(
                PedidoYaAsignadoException.class,
                () -> pedidoUseCase.obtenerPedidoPorId(11L, "EMPLEADO", 5L)
        );

        assertEquals(PEDIDO_YA_ASIGNADO, exception.getMessage());

    }

    @Test
    void obtenerPedidoPorId_deberiaLanzarExcepcion_siRolNoEsEmpleado() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pedidoUseCase.obtenerPedidoPorId(5L, "CLIENTE", 8L)
        );

        assertTrue(exception.getMessage().contains("Se requiere rol: EMPLEADO"));
        verifyNoInteractions(persistencePort);
    }

    @Test
    void notificarPedidoListo_deberiaActualizarEstadoYEnviarNotificacion_siTodoEsValido() {
        // Arrange
        Long idPedido = 100L;
        Long idEmpleado = 200L;
        String telefonoDestino = "+1234567890";
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(300L)
                .estado(EstadoPedido.EN_PREPARACION)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(300L);

        // Act
        assertDoesNotThrow(() -> pedidoUseCase.notificarPedidoListo(idPedido, idEmpleado, telefonoDestino, rol));

        // Assert
        verify(persistencePort).guardarPedido(argThat(p ->
                p.getEstado() == EstadoPedido.LISTO
        ));
        verify(notificacionSmsClient).enviarSms(eq(telefonoDestino), startsWith("Tu pedido está listo. PIN: "));
    }

    @Test
    void notificarPedidoListo_deberiaLanzarExcepcion_siPedidoNoPerteneceARestauranteDelEmpleado() {
        // Arrange
        Long idPedido = 1L;
        Long idEmpleado = 2L;
        String telefonoDestino = "+1234567890";
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(100L)
                .estado(EstadoPedido.EN_PREPARACION)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(200L); // restaurante diferente

        // Act & Assert
        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.notificarPedidoListo(idPedido, idEmpleado, telefonoDestino, rol)
        );

        assertEquals(PEDIDO_NO_PERTENECE_A_RESTAURANTE, exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
        verify(notificacionSmsClient, never()).enviarSms(any(), any());
    }

    @Test
    void marcarPedidoComoEntregado_deberiaActualizarEstado_siPinEsCorrectoYEstadoListo() {
        // Arrange
        Long idPedido = 1L;
        Long idEmpleado = 10L;
        int pinIngresado = 1234;
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(100L)
                .estado(EstadoPedido.LISTO)
                .pinSeguridad(pinIngresado)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(100L);

        // Act
        pedidoUseCase.marcarPedidoComoEntregado(idPedido, idEmpleado, pinIngresado, rol);

        // Assert
        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstado());
        verify(persistencePort).guardarPedido(pedido);
    }

    @Test
    void marcarPedidoComoEntregado_deberiaLanzarExcepcion_siPinEsIncorrecto() {
        // Arrange
        Long idPedido = 1L;
        Long idEmpleado = 10L;
        int pinCorrecto = 1234;
        int pinIngresado = 9999; // incorrecto
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(100L)
                .estado(EstadoPedido.LISTO)
                .pinSeguridad(pinCorrecto)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(100L);

        // Act & Assert
        com.pragma.powerup.plazoleta.domain.exception.PinInvalidoException exception = assertThrows(
                com.pragma.powerup.plazoleta.domain.exception.PinInvalidoException.class,
                () -> pedidoUseCase.marcarPedidoComoEntregado(idPedido, idEmpleado, pinIngresado, rol)
        );

        assertEquals("El PIN ingresado es incorrecto", exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
    }

    @Test
    void marcarPedidoComoEntregado_deberiaLanzarExcepcion_siEstadoNoEsListo() {
        // Arrange
        Long idPedido = 2L;
        Long idEmpleado = 20L;
        int pin = 1234;
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(200L)
                .estado(EstadoPedido.EN_PREPARACION) // no es LISTO
                .pinSeguridad(pin)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(200L);

        // Act & Assert
        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.marcarPedidoComoEntregado(idPedido, idEmpleado, pin, rol)
        );

        assertEquals("Solo se puede marcar como entregado un pedido en estado LISTO", exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
    }

    @Test
    void marcarPedidoComoEntregado_deberiaLanzarExcepcion_siPedidoNoPerteneceARestauranteDelEmpleado() {
        // Arrange
        Long idPedido = 3L;
        Long idEmpleado = 30L;
        int pin = 1234;
        String rol = "EMPLEADO";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(300L)
                .estado(EstadoPedido.LISTO)
                .pinSeguridad(pin)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(999L); // distinto

        // Act & Assert
        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.marcarPedidoComoEntregado(idPedido, idEmpleado, pin, rol)
        );

        assertEquals("El pedido no pertenece al restaurante del empleado", exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
    }

    @Test
    void cancelarPedido_deberiaCancelarPedido_siEstadoEsPendienteYClienteEsElDueño() {
        Long idPedido = 1L;
        Long idCliente = 100L;
        String rol = "CLIENTE";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .estado(EstadoPedido.PENDIENTE)
                .idCliente(idCliente)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);

        assertDoesNotThrow(() -> pedidoUseCase.cancelarPedido(idPedido, idCliente, rol));

        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
        verify(persistencePort).guardarPedido(pedido);
    }

    @Test
    void cancelarPedido_deberiaLanzarExcepcion_siEstadoNoEsPendiente() {
        Long idPedido = 2L;
        Long idCliente = 101L;
        String rol = "CLIENTE";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .estado(EstadoPedido.EN_PREPARACION)
                .idCliente(idCliente)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);

        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.cancelarPedido(idPedido, idCliente, rol)
        );

        assertEquals(PEDIDO_EN_PREPARACION_NO_CANCELABLE, exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
    }

    @Test
    void cancelarPedido_deberiaLanzarExcepcion_siPedidoNoPerteneceAlCliente() {
        Long idPedido = 3L;
        Long idCliente = 102L;
        String rol = "CLIENTE";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .estado(EstadoPedido.PENDIENTE)
                .idCliente(999L) // otro cliente
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);

        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.cancelarPedido(idPedido, idCliente, rol)
        );

        assertEquals(PEDIDO_NO_PERTENECE_A_CLIENTE, exception.getMessage());
        verify(persistencePort, never()).guardarPedido(any());
    }

    @Test
    void cancelarPedido_deberiaLanzarExcepcion_siRolNoEsCliente() {
        Long idPedido = 4L;
        Long idCliente = 103L;
        String rol = "EMPLEADO";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pedidoUseCase.cancelarPedido(idPedido, idCliente, rol)
        );

        assertTrue(exception.getMessage().contains("Se requiere rol: CLIENTE"));
        verifyNoInteractions(persistencePort);
    }


    @Test
    void realizarPedido_DeberiaGuardarHistorialDespuesDeGuardarPedido() {
        // Arrange
        Long idCliente = 1L;
        String rol = "CLIENTE";
        Pedido pedidoEntrada = Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(10L)
                .platos(List.of(new PedidoPlato(1L, 2)))
                .build();

        Pedido pedidoGuardado = Pedido.builder()
                .id(100L)
                .idCliente(idCliente)
                .idRestaurante(10L)
                .estado(EstadoPedido.PENDIENTE)
                .fecha(new Date())
                .platos(pedidoEntrada.getPlatos())
                .pinSeguridad(1234)
                .build();

        when(persistencePort.clienteTienePedidoEnProceso(idCliente)).thenReturn(false);
        when(persistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedidoGuardado);

        // Act
        pedidoUseCase.realizarPedido(pedidoEntrada, rol, idCliente);

        // Assert
        verify(historialEstadoClient).guardarHistorial(argThat(h ->
                h.getIdPedido().equals(100L) &&
                        h.getIdCliente().equals(idCliente) &&
                        h.getEstado().equals("PENDIENTE")
        ));
    }

    @Test
    void asignarPedido_DeberiaGuardarHistorialConEstadoEnPreparacion() {
        Pedido pedido = Pedido.builder()
                .id(10L)
                .estado(EstadoPedido.PENDIENTE)
                .idCliente(5L)
                .idRestaurante(1L)
                .build();

        when(persistencePort.obtenerPedidoPorId(10L)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(5L)).thenReturn(1L);

        pedidoUseCase.asignarPedido(10L, 5L, "EMPLEADO");

        verify(historialEstadoClient).guardarHistorial(argThat(h ->
                h.getIdPedido().equals(10L) &&
                        h.getIdCliente().equals(5L) &&
                        h.getEstado().equals("EN_PREPARACION")
        ));
    }

    @Test
    void notificarPedidoListo_deberiaGuardarHistorialConEstadoListo() {
        Long idPedido = 100L;
        Long idEmpleado = 200L;
        String telefonoDestino = "+1234567890";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idCliente(999L)
                .idRestaurante(300L)
                .estado(EstadoPedido.EN_PREPARACION)
                .pinSeguridad(1234)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(300L);

        pedidoUseCase.notificarPedidoListo(idPedido, idEmpleado, telefonoDestino, "EMPLEADO");

        verify(historialEstadoClient).guardarHistorial(argThat(h ->
                h.getIdPedido().equals(idPedido) &&
                        h.getIdCliente().equals(999L) &&
                        h.getEstado().equals("LISTO")
        ));
    }

    @Test
    void marcarPedidoComoEntregado_deberiaGuardarHistorialConEstadoEntregado() {
        Long idPedido = 1L;
        Long idEmpleado = 10L;
        int pin = 1234;

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idRestaurante(100L)
                .idCliente(50L)
                .estado(EstadoPedido.LISTO)
                .pinSeguridad(pin)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(empleadoRestaurantePort.obtenerIdRestaurantePorEmpleado(idEmpleado)).thenReturn(100L);

        pedidoUseCase.marcarPedidoComoEntregado(idPedido, idEmpleado, pin, "EMPLEADO");

        verify(historialEstadoClient).guardarHistorial(argThat(h ->
                h.getIdPedido().equals(idPedido) &&
                        h.getIdCliente().equals(50L) &&
                        h.getEstado().equals("ENTREGADO")
        ));
    }

    @Test
    void cancelarPedido_deberiaGuardarHistorialConEstadoCancelado() {
        Long idPedido = 2L;
        Long idCliente = 102L;

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .estado(EstadoPedido.PENDIENTE)
                .idCliente(idCliente)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);

        pedidoUseCase.cancelarPedido(idPedido, idCliente, "CLIENTE");

        verify(historialEstadoClient).guardarHistorial(argThat(h ->
                h.getIdPedido().equals(idPedido) &&
                        h.getIdCliente().equals(idCliente) &&
                        h.getEstado().equals("CANCELADO")
        ));
    }

    @Test
    void retornarHistorial_cuandoPedidoPerteneceAlCliente() {
        // Arrange
        Long idPedido = 1L;
        Long idCliente = 10L;
        String rol = "CLIENTE";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idCliente(idCliente)
                .build();

        List<HistorialEstadoResponseDto> historialEsperado = List.of(
                new HistorialEstadoResponseDto("PENDIENTE", new Date()),
                new HistorialEstadoResponseDto("EN_PREPARACION", new Date())
        );

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);
        when(historialEstadoClient.obtenerHistorial(idPedido, idCliente)).thenReturn(historialEsperado);

        // Act
        List<HistorialEstadoResponseDto> resultado = pedidoUseCase.consultarHistorialDePedido(idPedido, idCliente, rol);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
        verify(persistencePort).obtenerPedidoPorId(idPedido);
        verify(historialEstadoClient).obtenerHistorial(idPedido, idCliente);
    }

    @Test
    void enviarExcepcion_cuandoPedidoNoPerteneceAlCliente() {
        // Arrange
        Long idPedido = 1L;
        Long idCliente = 10L;
        Long otroCliente = 20L;
        String rol = "CLIENTE";

        Pedido pedido = Pedido.builder()
                .id(idPedido)
                .idCliente(otroCliente)
                .build();

        when(persistencePort.obtenerPedidoPorId(idPedido)).thenReturn(pedido);

        // Act & Assert
        EstadoPedidoInvalidoException exception = assertThrows(
                EstadoPedidoInvalidoException.class,
                () -> pedidoUseCase.consultarHistorialDePedido(idPedido, idCliente, rol)
        );

        assertEquals(PEDIDO_NO_PERTENECE_A_CLIENTE, exception.getMessage());
        verify(persistencePort).obtenerPedidoPorId(idPedido);
        verifyNoInteractions(historialEstadoClient);
    }


}