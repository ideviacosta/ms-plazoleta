package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.domain.exception.PropietarioInvalidoException;
import com.pragma.powerup.plazoleta.domain.exception.ValidacionCampoException;
import com.pragma.powerup.plazoleta.domain.model.PaginaRespuesta;
import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;


import java.util.List;

import static com.pragma.powerup.plazoleta.util.MensajesError.*;
import static com.pragma.powerup.plazoleta.util.RolValidator.*;
import static com.pragma.powerup.plazoleta.util.Roles.*;


public class PlatoUseCase implements IPlatoService {

    private final IPlatoPersistencePort persistencePort;
    private final IRestauranteValidationPort restauranteValidationPort;

    public PlatoUseCase(IPlatoPersistencePort persistencePort, IRestauranteValidationPort restauranteValidationPort) {
        this.persistencePort = persistencePort;
        this.restauranteValidationPort = restauranteValidationPort;
    }

    @Override
    public void crearPlato(Plato plato, String rol, Long idPropietario) {
        validarRol(rol, PROPIETARIO);

        if (!restauranteValidationPort.esPropietarioDelRestaurante(idPropietario, plato.getIdRestaurante())) {
            throw new PropietarioInvalidoException(PROPIETARIO_NO_DUENIO_RESTAURANTE);
        }

        if (plato.getPrecio() == null || plato.getPrecio() <= 0) {
            throw new ValidacionCampoException(PRECIO_INVALIDO);
        }

        if (isEmpty(plato.getNombre()) || isEmpty(plato.getDescripcion()) || isEmpty(plato.getUrlImagen())) {
            throw new ValidacionCampoException(CAMPOS_OBLIGATORIOS);
        }

        if (plato.getIdCategoria() == null) {
            throw new ValidacionCampoException(CATEGORIA_OBLIGATORIA);
        }

        plato.setActivo(true);
        persistencePort.guardarPlato(plato);
    }

    @Override
    public void modificarPlato(Long idPlato, Integer nuevoPrecio, String nuevaDescripcion, String rol, Long idPropietario) {
        validarRol(rol, PROPIETARIO);

        if (nuevoPrecio == null || nuevoPrecio <= 0 || isEmpty(nuevaDescripcion)) {
            throw new ValidacionCampoException(PRECIO_Y_DESCRIPCION_INVALIDOS);
        }

        if (!restauranteValidationPort.esPropietarioDelPlato(idPlato, idPropietario)) {
            throw new PropietarioInvalidoException(PROPIETARIO_NO_DUENIO_PLATO);
        }

        persistencePort.actualizarPlato(idPlato, nuevoPrecio, nuevaDescripcion);
    }

    private boolean isEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    @Override
    public void cambiarEstadoPlato(Long idPlato, boolean habilitar, String rol, Long idPropietario) {
        validarRol(rol, PROPIETARIO);

        if (!restauranteValidationPort.esPropietarioDelPlato(idPlato, idPropietario)) {
            throw new PropietarioInvalidoException(PROPIETARIO_NO_DUENIO_PLATO);
        }

        persistencePort.cambiarEstadoPlato(idPlato, habilitar);
    }

    @Override
    public PaginaRespuesta<Plato> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int page, int size, String rol) {
        validarRol(rol, CLIENTE);
        return persistencePort.listarPlatosPorRestaurante(idRestaurante, idCategoria, page, size);
    }

}
