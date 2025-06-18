package com.pragma.powerup.plazoleta.infraestructure.input.rest.dto;

public class NotificacionSmsRequestDto {
    private String telefonoDestino;
    private String mensaje;

    public NotificacionSmsRequestDto(String telefonoDestino, String mensaje) {
        this.telefonoDestino = telefonoDestino;
        this.mensaje = mensaje;
    }

    public String getTelefonoDestino() {
        return telefonoDestino;
    }

    public void setTelefonoDestino(String telefonoDestino) {
        this.telefonoDestino = telefonoDestino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
