package com.pragma.powerup.plazoleta.application.usecase;

import com.pragma.powerup.plazoleta.domain.api.IPlatoService;
import com.pragma.powerup.plazoleta.domain.model.Plato;
import com.pragma.powerup.plazoleta.domain.spi.IPlatoPersistencePort;
import com.pragma.powerup.plazoleta.domain.spi.IRestauranteValidationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@RequiredArgsConstructor
public class PlatoUseCase implements IPlatoService {

    private final IPlatoPersistencePort persistencePort;
    @Qualifier("restauranteJpaAdapter")
    private final IRestauranteValidationPort restauranteValidationPort;

    @Override
    public void crearPlato(Plato plato, String rol, Long idPropietario) {
        if (!"PROPIETARIO".equals(rol)) {
            throw new RuntimeException("Solo el propietario puede crear platos");
        }

        if (!restauranteValidationPort.esPropietarioDelRestaurante(idPropietario, plato.getIdRestaurante())) {
            throw new RuntimeException("No puede crear platos para un restaurante que no le pertenece");
        }

        if (plato.getPrecio() == null || plato.getPrecio() <= 0) {
            throw new RuntimeException("El precio debe ser un número entero positivo mayor a 0");
        }

        if (isEmpty(plato.getNombre()) || isEmpty(plato.getDescripcion()) || isEmpty(plato.getUrlImagen())) {
            throw new RuntimeException("Todos los campos obligatorios deben estar diligenciados");
        }

        if (plato.getIdCategoria() == null) {
            throw new RuntimeException("El plato debe tener una categoría");
        }

        plato.setActivo(true);
        persistencePort.guardarPlato(plato);
    }

    private boolean isEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    @Override
    public void modificarPlato(Long idPlato, Integer nuevoPrecio, String nuevaDescripcion, String rol, Long idPropietario) {
        System.out.println("===> Iniciando modificación de plato:");
        System.out.println("idPlato = " + idPlato);
        System.out.println("nuevoPrecio = " + nuevoPrecio);
        System.out.println("nuevaDescripcion = " + nuevaDescripcion);
        System.out.println("rol = " + rol);
        System.out.println("idPropietario = " + idPropietario);

        if (!"PROPIETARIO".equals(rol)) {
            System.out.println("❌ Rol no autorizado");
            throw new RuntimeException("Solo el propietario puede modificar platos");
        }

        if (nuevoPrecio == null || nuevoPrecio <= 0 || nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty()) {
            System.out.println("❌ Precio o descripción inválidos");
            throw new RuntimeException("Precio y descripción válidos son obligatorios");
        }

        boolean esPropietario = restauranteValidationPort.esPropietarioDelPlato(idPlato, idPropietario);
        System.out.println("¿Es propietario del plato?: " + esPropietario);

        if (!esPropietario) {
            System.out.println("❌ El propietario no tiene permiso sobre este plato");
            throw new RuntimeException("No puede modificar platos de un restaurante que no le pertenece");
        }

        System.out.println("✅ Validaciones pasadas, procediendo a actualizar el plato...");
        persistencePort.actualizarPlato(idPlato, nuevoPrecio, nuevaDescripcion);
        System.out.println("✅ Plato actualizado exitosamente");
    }


}