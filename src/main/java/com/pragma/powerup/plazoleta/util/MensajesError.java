package com.pragma.powerup.plazoleta.util;

public class MensajesError {
    private MensajesError() {
    }
    public static final String SOLO_PROPIETARIO_PUEDE_CREAR = "Solo el propietario puede crear platos";
    public static final String PROPIETARIO_NO_DUENIO_RESTAURANTE = "No puede crear platos para un restaurante que no le pertenece";
    public static final String PRECIO_INVALIDO = "El precio debe ser un número entero positivo mayor a 0";
    public static final String CAMPOS_OBLIGATORIOS = "Todos los campos obligatorios deben estar diligenciados";
    public static final String CATEGORIA_OBLIGATORIA = "El plato debe tener una categoría";
    public static final String SOLO_PROPIETARIO_PUEDE_MODIFICAR = "Solo el propietario puede modificar platos";
    public static final String PRECIO_Y_DESCRIPCION_INVALIDOS = "Precio y descripción válidos son obligatorios";
    public static final String PROPIETARIO_NO_DUENIO_PLATO = "No puede modificar platos de un restaurante que no le pertenece";
    public static final String SOLO_ADMIN_PUEDE_CREAR_RESTAURANTE = "Solo un administrador puede crear restaurantes";
    public static final String PROPIETARIO_INVALIDO = "Propietario inválido: no existe o no tiene rol PROPIETARIO";
    public static final String PLATO_NO_EXISTE = "Plato no existe";
    public static final String PEDIDO_EN_PROCESO = "Pedido en proceso";
    public static final String SOLO_SE_PUEDE_ASIGNAR_PEDIDOS_EN_PENDIENTE ="Solo se pueden asignar pedidos en estado PENDIENTE";
    public static final String PEDIDO_NO_EXISTE = "Pedido no encontrado";
    public static final String PEDIDO_YA_ASIGNADO = "Pedido ya asignado";
    public static final String EMPLEADO_NO_ASOCIADO_RESTAURANTE ="Empleado no está asociado a ningún restaurante";
    public static final String PEDIDO_NO_PERTENECE_A_RESTAURANTE = "El pedido no pertenece al restaurante del empleado";
    public static final String PEDIDO_LISTO_CODIGO_DE_ENTREGA ="Tu pedido está listo. PIN: ";
    public static final String PIN_INCORRECTO = "El PIN ingresado es incorrecto";
    public static final String SOLO_SE_PUEDE_MARCAR_ENTREGADO_SI_LISTO = "Solo se puede marcar como entregado un pedido en estado LISTO";
    public static final String PEDIDO_NO_PERTENECE_A_CLIENTE = "Este pedido no pertenece al cliente autenticado.";
    public static final String PEDIDO_EN_PREPARACION_NO_CANCELABLE ="Lo sentimos, tu pedido ya está en preparación y no puede cancelarse.";
}