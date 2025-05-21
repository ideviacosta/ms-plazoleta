package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PlatoEntity;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.PlatoEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
public class PlatoJpaAdapter implements IPlatoPersistencePort {

    private final IPlatoRepository platoRepository;

    @Override
    public void guardarPlato(Plato plato) {
        PlatoEntity entity = PlatoEntityMapper.toEntity(plato);
        platoRepository.save(entity);
    }

    @Override
    public void actualizarPlato(Long idPlato, Integer nuevoPrecio, String nuevaDescripcion) {
        Optional<PlatoEntity> optional = platoRepository.findById(idPlato);
        if (optional.isEmpty()) {
            throw new RuntimeException("El plato no existe");
        }
        PlatoEntity plato = optional.get();
        plato.setPrecio(nuevoPrecio);
        plato.setDescripcion(nuevaDescripcion);
        platoRepository.save(plato);
    }

}