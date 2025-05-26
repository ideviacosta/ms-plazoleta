package com.pragma.powerup.plazoleta.util;
import com.pragma.powerup.plazoleta.domain.exception.RolNoAutorizadoException;
import java.util.Arrays;
import java.util.List;

public class RolValidator {

    private RolValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Valida que el rol actual sea igual al requerido.
     * @param actual   El rol proporcionado.
     * @param requerido El rol esperado.
     */
    public static void validarRol(String actual, String requerido) {
        if (actual == null || !requerido.equalsIgnoreCase(actual.trim())) {
            throw new RolNoAutorizadoException("Se requiere rol: " + requerido + ", pero se recibió: " + actual);
        }
    }

    /**
     * Valida que el rol actual esté dentro de los roles permitidos.
     * @param actual El rol actual.
     * @param rolesPermitidos Uno o más roles válidos.
     */
    public static void validarRolEn(String actual, String... rolesPermitidos) {
        List<String> permitidos = Arrays.stream(rolesPermitidos)
                .map(String::toUpperCase)
                .toList();

        if (!permitidos.contains(actual.trim().toUpperCase())) {
            throw new RolNoAutorizadoException("Rol no autorizado: " + actual + ". Se esperaba uno de: " + String.join(", ", permitidos));
        }
    }
}
