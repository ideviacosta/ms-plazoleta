package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.api.IPedidoService;
import com.pragma.powerup.plazoleta.domain.exception.PedidoEnProcesoException;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.spi.IPedidoPersistencePort;

import java.time.Instant;
import java.util.Date;

import static com.pragma.powerup.plazoleta.util.MensajesError.PEDIDO_EN_PROCESO;
import static com.pragma.powerup.plazoleta.util.RolValidator.validarRol;
import static com.pragma.powerup.plazoleta.util.Roles.CLIENTE;

public class PedidoUseCase implements IPedidoService {

    private final IPedidoPersistencePort persistencePort;

    public PedidoUseCase(IPedidoPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
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

}