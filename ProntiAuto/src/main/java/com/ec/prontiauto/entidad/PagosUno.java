package com.ec.prontiauto.entidad;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "pagos1")
public class PagosUno extends AbstractEntities {
    @Column(name = "\"fechaUltimoPago\"", nullable = false)
    @CsvBindByName(column = "fecha_ultimo_pago")
    private Date fechaUltimoPago;

    @Column(name = "\"fechaInicio\"", nullable = false)
    @CsvBindByName(column = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "\"fechaFin\"", nullable = false)
    @CsvBindByName(column = "fecha_fin")
    private Date fechaFin;

    @Column(name = "nombre", nullable = false)
    @CsvBindByName(column = "nombre")
    private String nombre;

    @Column(name = "periodo", nullable = false)
    @CsvBindByName(column = "periodo")
    private String periodo;

    @OneToMany(mappedBy = "idPagosUno")
    private List<PagosDos> pagosDosCollection;


    public PagosUno() {
    }

    public PagosUno setValoresDiferentes(PagosUno regAntiguo, PagosUno regActualizar){
        if(regActualizar.getFechaUltimoPago() != null){
            regAntiguo.setFechaUltimoPago(regActualizar.getFechaUltimoPago());
        }
        if(regActualizar.getFechaInicio() != null){
            regAntiguo.setFechaInicio(regActualizar.getFechaInicio());
        }
        if(regActualizar.getFechaFin() != null){
            regAntiguo.setFechaFin(regActualizar.getFechaFin());
        }
        if(regActualizar.getNombre() != null){
            regAntiguo.setNombre(regActualizar.getNombre());
        }
        if(regActualizar.getPeriodo() != null){
            regAntiguo.setPeriodo(regActualizar.getPeriodo());
        }

        if(regActualizar.getSisHabilitado() != null){
            regAntiguo.setSisHabilitado(regActualizar.getSisHabilitado());
        }

        return regAntiguo;
    }

    public Date getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(Date fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<PagosDos> getPagosDosCollection() {
        return pagosDosCollection;
    }

    public void setPagosDosCollection(List<PagosDos> pagosDosCollection) {
        this.pagosDosCollection = pagosDosCollection;
    }
}
