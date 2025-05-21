package com.pragma.powerup.plazoleta.domain.spi;

public interface IRestauranteValidationPort {
    boolean esPropietarioDelRestaurante(Long idPropietario, Long idRestaurante);
    boolean esPropietarioDelPlato(Long idPlato, Long idPropietario);

}