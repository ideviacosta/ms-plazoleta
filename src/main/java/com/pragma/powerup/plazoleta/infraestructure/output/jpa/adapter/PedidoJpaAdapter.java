package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.spi.IPedidoPersistencePort;

import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.PedidoEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IPedidoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;

    @Override
    public void guardarPedido(Pedido pedido) {
        pedidoRepository.save(PedidoEntityMapper.toEntity(pedido));
    }

    @Override
    public boolean clienteTienePedidoEnProceso(Long idCliente) {
        return pedidoRepository.existsByIdClienteAndEstadoIn(
                idCliente,
                List.of(
                        EstadoPedido.PENDIENTE,
                        EstadoPedido.EN_PREPARACION,
                        EstadoPedido.LISTO
                )
        );
    }

}