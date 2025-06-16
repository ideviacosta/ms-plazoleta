package com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository;

import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.EmpleadoRestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmpleadoRestauranteRepository extends JpaRepository<EmpleadoRestauranteEntity, Long> {
    Optional<EmpleadoRestauranteEntity> findByIdEmpleado(Long idEmpleado);
}