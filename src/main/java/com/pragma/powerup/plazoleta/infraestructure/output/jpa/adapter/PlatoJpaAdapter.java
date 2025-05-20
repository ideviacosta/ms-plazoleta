package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PlatoEntity;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.PlatoEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
public class PlatoJpaAdapter implements IPlatoPersistencePort {

    private final IPlatoRepository platoRepository;

    @Override
    public void guardarPlato(Plato plato) {
        PlatoEntity entity = PlatoEntityMapper.toEntity(plato);
        platoRepository.save(entity);
    }
}