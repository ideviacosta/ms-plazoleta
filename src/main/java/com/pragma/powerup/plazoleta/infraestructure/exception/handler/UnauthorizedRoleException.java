package com.pragma.powerup.plazoleta.infraestructure.exception.handler;

public class UnauthorizedRoleException extends RuntimeException {
    public UnauthorizedRoleException(String message) {
        super(message);
    }
}
