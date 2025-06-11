package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.model.PedidoPlato;
import com.pragma.powerup.plazoleta.domain.spi.IPedidoPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pragma.powerup.plazoleta.util.MensajesError.PEDIDO_EN_PROCESO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoUseCaseTest {

    private IPedidoPersistencePort persistencePort;
    private PedidoUseCase pedidoUseCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IPedidoPersistencePort.class);
        pedidoUseCase = new PedidoUseCase(persistencePort);
    }

    @Test
    void realizarPedido_DeberiaGuardarPedido_SiClienteNoTienePedidosEnProceso() {
        // Arrange
        Long idCliente = 1L;
        String rol = "CLIENTE";
        Pedido pedido = Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(10L)
                .platos(List.of(new PedidoPlato(1L, 2)))
                .build();

        when(persistencePort.clienteTienePedidoEnProceso(idCliente)).thenReturn(false);

        // Act
        assertDoesNotThrow(() -> pedidoUseCase.realizarPedido(pedido, rol, idCliente));

        // Assert
        verify(persistencePort, times(1)).guardarPedido(any(Pedido.class));
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
    void realizarPedido_DeberiaEstablecerEstadoPendienteYFecha() {
        // Arrange
        Long idCliente = 3L;
        String rol = "CLIENTE";
        Pedido pedido = Pedido.builder()
                .idCliente(idCliente)
                .idRestaurante(30L)
                .platos(List.of(new PedidoPlato(3L, 3)))
                .build();

        when(persistencePort.clienteTienePedidoEnProceso(idCliente)).thenReturn(false);

        // Act
        pedidoUseCase.realizarPedido(pedido, rol, idCliente);

        // Assert
        verify(persistencePort).guardarPedido(argThat(p ->
                p.getEstado() == EstadoPedido.PENDIENTE &&
                        p.getFecha() != null &&
                        p.getIdCliente().equals(idCliente)
        ));
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

}