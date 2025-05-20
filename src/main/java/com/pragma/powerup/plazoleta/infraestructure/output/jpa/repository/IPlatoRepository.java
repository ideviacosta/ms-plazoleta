package com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository;

import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PlatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlatoRepository extends JpaRepository<PlatoEntity, Long> {
}