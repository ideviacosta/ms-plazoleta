package com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository;

import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.RestauranteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
    boolean existsByIdAndIdPropietario(Long id, Long idPropietario);
    Page<RestauranteEntity> findAllByOrderByNombreAsc(Pageable pageable);

}