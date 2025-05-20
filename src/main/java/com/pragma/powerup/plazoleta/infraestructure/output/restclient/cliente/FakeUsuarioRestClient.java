package com.pragma.powerup.plazoleta.infraestructure.output.restclient.cliente;

import com.pragma.powerup.plazoleta.infraestructure.output.restclient.dto.UsuarioResponseDto;

public class FakeUsuarioRestClient extends UsuarioRestClient {

    public FakeUsuarioRestClient() {
        super(null, null); // no necesitamos RestTemplate ni Config en pruebas
    }

    private boolean devolverNull = false;
    private boolean devolverPropietario = false;

    public void setDevolverNull(boolean valor) {
        this.devolverNull = valor;
    }

    public void setDevolverPropietario(boolean valor) {
        this.devolverPropietario = valor;
    }

    @Override
    public UsuarioResponseDto obtenerUsuarioPorId(Long idUsuario) {
        if (devolverNull) {
            throw new RuntimeException("El usuario con id " + idUsuario + " no existe.");
        }
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(idUsuario);
        dto.setRol(devolverPropietario ? "PROPIETARIO" : "ADMINISTRADOR");
        return dto;
    }
}
