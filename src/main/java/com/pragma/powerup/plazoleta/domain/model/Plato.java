package com.pragma.powerup.plazoleta.domain.model;


public class Plato {
    private Long id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private Long idCategoria;
    private Long idRestaurante;
    private Boolean activo;

    private Plato(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.precio = builder.precio;
        this.descripcion = builder.descripcion;
        this.urlImagen = builder.urlImagen;
        this.idCategoria = builder.idCategoria;
        this.idRestaurante = builder.idRestaurante;
        this.activo = builder.activo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String nombre;
        private Integer precio;
        private String descripcion;
        private String urlImagen;
        private Long idCategoria;
        private Long idRestaurante;
        private Boolean activo;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        public Builder precio(Integer precio) {
            this.precio = precio;
            return this;
        }
        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }
        public Builder urlImagen(String urlImagen) {
            this.urlImagen = urlImagen;
            return this;
        }
        public Builder idCategoria(Long idCategoria) {
            this.idCategoria = idCategoria;
            return this;
        }
        public Builder idRestaurante(Long idRestaurante) {
            this.idRestaurante = idRestaurante;
            return this;
        }
        public Builder activo(Boolean activo) {
            this.activo = activo;
            return this;
        }
        public Plato build() {
            return new Plato(this);
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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}