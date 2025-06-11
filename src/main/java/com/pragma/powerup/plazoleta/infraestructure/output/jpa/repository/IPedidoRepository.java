package com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository;

import com.pragma.powerup.plazoleta.domain.model.EstadoPedido;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long> {
    boolean existsByIdClienteAndEstadoIn(Long idCliente, List<EstadoPedido> estados);
}