package com.pragma.powerup.plazoleta.domain.model;

import java.util.List;

public class PaginaRespuesta<T> {
    private List<T> contenido;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int elementosPorPagina;

    public PaginaRespuesta(List<T> contenido, int paginaActual, int totalPaginas, long totalElementos, int elementosPorPagina) {
        this.contenido = contenido;
        this.paginaActual = paginaActual;
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
        this.elementosPorPagina = elementosPorPagina;
    }

    public List<T> getContenido() {
        return contenido;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public int getElementosPorPagina() {
        return elementosPorPagina;
    }
}