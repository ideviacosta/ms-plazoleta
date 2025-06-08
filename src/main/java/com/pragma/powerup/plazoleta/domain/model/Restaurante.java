package com.pragma.powerup.plazoleta.domain.model;

public class Restaurante {
    private Long id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long idPropietario;

    public static Builder builder() {
        return new Builder();
    }

    private Restaurante(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.nit = builder.nit;
        this.direccion = builder.direccion;
        this.telefono = builder.telefono;
        this.urlLogo = builder.urlLogo;
        this.idPropietario = builder.idPropietario;
    }

    public static class Builder {
        private Long id;
        private String nombre;
        private String nit;
        private String direccion;
        private String telefono;
        private String urlLogo;
        private Long idPropietario;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        public Builder nit(String nit) {
            this.nit = nit;
            return this;
        }
        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }
        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        public Builder urlLogo(String urlLogo) {
            this.urlLogo = urlLogo;
            return this;
        }
        public Builder idPropietario(Long idPropietario) {
            this.idPropietario = idPropietario;
            return this;
        }
        public Restaurante build() {
            return new Restaurante(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Long idPropietario) {
        this.idPropietario = idPropietario;
    }
}