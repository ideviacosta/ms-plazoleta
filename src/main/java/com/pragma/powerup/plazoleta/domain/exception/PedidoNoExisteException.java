package com.pragma.powerup.plazoleta.domain.exception;

public class PedidoNoExisteException extends RuntimeException {
    public PedidoNoExisteException(String message) {
        super(message);
    }
}
