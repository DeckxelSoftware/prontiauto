package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "libro_biblioteca")
public class LibroBiblioteca extends AbstractEntities {

    @Column(name = "\"generoLibro\"", nullable = false, length = 255)
    @CsvBindByName(column = "genero_libro")
    private String generoLibro;

    @Column(name = "isbn", nullable = false, unique = true, length = 255)
    @CsvBindByName(column = "isbn")
    private String isbn;

    @Column(name = "nombre", length = 50, nullable = false)
    @CsvBindByName(column = "nombre")
    private String nombre;

    @Column(name = "descripcion", length = 2255)
    @CsvBindByName(column = "descripcion")
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "idLibroBiblioteca")
    private List<AutorLibro> autorLibroCollection;

    public LibroBiblioteca() {
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

    public List<AutorLibro> getAutorLibroCollection() {
        return autorLibroCollection;
    }

    public void setAutorLibroCollection(List<AutorLibro> autorLibroCollection) {
        this.autorLibroCollection = autorLibroCollection;
    }

    public LibroBiblioteca setValoresDiferentes(LibroBiblioteca registroAntiguo, LibroBiblioteca registroActualizar) {
        if (registroActualizar.getNombre() != null) {
            registroAntiguo.setNombre(registroActualizar.getNombre());
        }
        if (registroActualizar.getDescripcion() != null) {
            registroAntiguo.setDescripcion(registroActualizar.getDescripcion());
        }
        if (registroActualizar.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
        }
        if (registroActualizar.getGeneroLibro() != null) {
            registroAntiguo.setGeneroLibro(registroActualizar.getGeneroLibro());
        }
        if (registroActualizar.getIsbn() != null) {
            registroAntiguo.setIsbn(registroActualizar.getIsbn());
        }
        return registroAntiguo;
    }
}
