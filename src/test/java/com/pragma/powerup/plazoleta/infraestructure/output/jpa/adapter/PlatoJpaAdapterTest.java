package com.pragma.powerup.plazoleta.infraestructure.output.jpa.adapter;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.entity.PlatoEntity;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.mapper.PlatoEntityMapper;
import com.pragma.powerup.plazoleta.infraestructure.output.jpa.repository.IPlatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PlatoJpaAdapterTest {

    private IPlatoRepository platoRepository;
    private PlatoJpaAdapter platoJpaAdapter;

    @BeforeEach
    void setUp() {
        platoRepository = mock(IPlatoRepository.class);
        platoJpaAdapter = new PlatoJpaAdapter(platoRepository);
    }

    @Test
    void guardarPlato_debeInvocarRepositorySave() {
        Plato plato = new Plato(null, "Pizza", 12000, "Margarita", "http://img.com", 1L, 1L, true);
        PlatoEntity entity = PlatoEntityMapper.toEntity(plato);

        platoJpaAdapter.guardarPlato(plato);

        verify(platoRepository, times(1)).save(any(PlatoEntity.class));
    }
}