package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class LibroBibliotecaResponseDao extends AbstractResponseDao {
    private String generoLibro;
    private String isbn;
    private String nombre;
    private String descripcion;
    private List<AutorLibroResponseDao> autorLibroCollection;

    public LibroBibliotecaResponseDao() {
    }

    public String getGeneroLibro() {
        return generoLibro;
    }

    public void setGeneroLibro(String generoLibro) {
        this.generoLibro = generoLibro;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<AutorLibroResponseDao> getAutorLibroCollection() {
        return autorLibroCollection;
    }

    public void setAutorLibroCollection(List<AutorLibroResponseDao> autorLibroCollection) {
        this.autorLibroCollection = autorLibroCollection;
    }

}
