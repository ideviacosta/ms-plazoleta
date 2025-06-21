package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.exception.EstadoPedidoInvalidoException;
import com.pragma.powerup.plazoleta.domain.exception.PedidoNoExisteException;
import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Pedido;
import com.pragma.powerup.plazoleta.domain.spi.IPedidoPersistencePort;

import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PedidoEntity;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.PedidoEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.pragma.powerup.plazoleta.util.MensajesError.*;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;

    @Override
    public Pedido guardarPedido(Pedido pedido) {
        PedidoEntity entity = PedidoEntityMapper.toEntity(pedido);
        PedidoEntity savedEntity = pedidoRepository.save(entity);
        return PedidoEntityMapper.toModel(savedEntity);
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

    @Override
    public void asignarPedido(Long idPedido, Long idEmpleado) {
        PedidoEntity pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNoExisteException(PEDIDO_NO_EXISTE));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new EstadoPedidoInvalidoException(SOLO_SE_PUEDE_ASIGNAR_PEDIDOS_EN_PENDIENTE);
        }

        pedido.setIdEmpleadoAsignado(idEmpleado);
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        pedidoRepository.save(pedido);
    }

    @Override
    public Pedido obtenerPedidoPorId(Long idPedido) {
        PedidoEntity entity = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNoExisteException(PEDIDO_NO_EXISTE));
        return PedidoEntityMapper.toModel(entity);
    }

    @Override
    public PaginaRespuesta<Pedido> listarPedidosPorEstadoYEmpleado(
            Long idRestaurante, Long idEmpleado, EstadoPedido estado, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PedidoEntity> pageResult = pedidoRepository.findByIdEmpleadoAsignadoAndEstado(
                idEmpleado, estado, pageable
        );

        List<Pedido> contenido = pageResult.getContent().stream()
                .map(PedidoEntityMapper::toModel)
                .toList();

        return new PaginaRespuesta<>(
                contenido,
                pageResult.getNumber(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements(),
                pageResult.getSize()
        );
    }


}