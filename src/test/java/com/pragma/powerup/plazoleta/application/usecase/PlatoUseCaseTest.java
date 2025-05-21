package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatoUseCaseTest {

    private IPlatoPersistencePort persistencePort;
    private IRestauranteValidationPort validationPort;
    private PlatoUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IPlatoPersistencePort.class);
        validationPort = mock(IRestauranteValidationPort.class);
        useCase = new PlatoUseCase(persistencePort, validationPort);
    }

    @Test
    void crearPlato_exitoso() {
        Plato plato = new Plato(null, "Pizza", 12000, "Pizza margarita", "http://img.com/pizza.jpg", 1L, 1L, null);
        when(validationPort.esPropietarioDelRestaurante(2L, 1L)).thenReturn(true);

        useCase.crearPlato(plato, "PROPIETARIO", 2L);

        verify(persistencePort).guardarPlato(any(Plato.class));
        assertTrue(plato.getActivo());
    }

    @Test
    void crearPlato_fallaPorRolNoValido() {
        Plato plato = new Plato(null, "Pizza", 12000, "Pizza margarita", "http://img.com/pizza.jpg", 1L, 1L, null);

        Exception ex = assertThrows(RuntimeException.class, () ->
                useCase.crearPlato(plato, "ADMIN", 2L));
        System.out.println("Mensaje real: " + ex.getMessage());
        assertEquals("Solo el propietario puede crear platos", ex.getMessage());
    }

    @Test
    void crearPlato_fallaPorPrecioInvalido() {
        Plato plato = new Plato(null, "Pizza", 0, "desc", "http://img.com", 1L, 1L, null);
        when(validationPort.esPropietarioDelRestaurante(2L, 1L)).thenReturn(true);
        Exception ex = assertThrows(RuntimeException.class, () -> {
            useCase.crearPlato(plato, "PROPIETARIO", 2L);
        });

        System.out.println("💥 Mensaje real: " + ex.getMessage());
        assertTrue(ex.getMessage() != null && ex.getMessage().toLowerCase().contains("precio"));
    }

    @Test
    void crearPlato_fallaPorCamposVacios() {
        Plato plato = new Plato(null, "", 10000, "", "", 1L, 1L, null);
        when(validationPort.esPropietarioDelRestaurante(2L, 1L)).thenReturn(true);
        Exception ex = assertThrows(RuntimeException.class, () -> {
            useCase.crearPlato(plato, "PROPIETARIO", 2L);
        });

        System.out.println("💥 Mensaje real (campos vacíos): " + ex.getMessage());
        assertTrue(ex.getMessage().contains("obligatorios"));
    }

    @Test
    void crearPlato_fallaSiCategoriaEsNull() {
        Plato plato = new Plato(null, "Arroz", 10000, "desc", "http://img.com", null, 1L, null);
        when(validationPort.esPropietarioDelRestaurante(2L, 1L)).thenReturn(true);
        Exception ex = assertThrows(RuntimeException.class, () -> {
            useCase.crearPlato(plato, "PROPIETARIO", 2L);
        });

        System.out.println("💥 Mensaje real (categoría): " + ex.getMessage());
        assertTrue(ex.getMessage().contains("categoría"));
    }


    @Test
    void crearPlato_fallaSiNoEsPropietarioDelRestaurante() {
        Plato plato = new Plato(null, "Sopa", 8000, "desc", "http://img.com", 1L, 1L, null);
        when(validationPort.esPropietarioDelRestaurante(2L, 1L)).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () ->
                useCase.crearPlato(plato, "PROPIETARIO", 2L));
        System.out.println("Mensaje real: " + ex.getMessage());
        assertEquals("No puede crear platos para un restaurante que no le pertenece", ex.getMessage());
    }

    @Test
    void modificarPlato_ok() {
        when(validationPort.esPropietarioDelPlato(1L, 10L)).thenReturn(true);

        assertDoesNotThrow(() ->
                useCase.modificarPlato(1L, 20000, "Nueva descripción", "PROPIETARIO", 10L)
        );

        verify(persistencePort).actualizarPlato(1L, 20000, "Nueva descripción");
    }

    @Test
    void modificarPlato_fallaSiNoEsPropietario() {
        when(validationPort.esPropietarioDelPlato(1L, 99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                useCase.modificarPlato(1L, 20000, "Nueva", "PROPIETARIO", 99L)
        );

        assertEquals("No puede modificar platos de un restaurante que no le pertenece", exception.getMessage());
    }

    @Test
    void modificarPlato_fallaSiRolNoEsPropietario() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                useCase.modificarPlato(1L, 15000, "Descripción", "ADMIN", 10L)
        );

        assertEquals("Solo el propietario puede modificar platos", exception.getMessage());
    }

    @Test
    void modificarPlato_fallaSiPrecioODescripcionInvalidos() {
        RuntimeException ex1 = assertThrows(RuntimeException.class, () ->
                useCase.modificarPlato(1L, 0, "desc", "PROPIETARIO", 10L)
        );
        assertEquals("Precio y descripción válidos son obligatorios", ex1.getMessage());

        RuntimeException ex2 = assertThrows(RuntimeException.class, () ->
                useCase.modificarPlato(1L, 10000, "", "PROPIETARIO", 10L)
        );
        assertEquals("Precio y descripción válidos son obligatorios", ex2.getMessage());
    }
}