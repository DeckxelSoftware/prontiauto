package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "pago")
public class Pago extends AbstractEntities {
    @Column(name = "\"tipoDocumento\"", length = 255, nullable = false)
    @CsvBindByName(column = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "\"observaciones\"", length = 255)
    @CsvBindByName(column = "observaciones")
    private String observaciones;

    @Column(name = "\"numeroDocumento\"", length = 255, nullable = false)
    @CsvBindByName(column = "numero_documento")
    private String numeroDocumento;

    @Column(name = "valor", nullable = false)
    @CsvBindByName(column = "valor")
    private BigDecimal valor;

    @Column(name = "\"fechaDeposito\"", nullable = false)
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "fecha_deposito")
    private Date fechaDeposito;

    @ManyToOne
    @JoinColumn(name = "\"idCuentaBancariaEmpresa\"", referencedColumnName = "\"id\"", nullable = false)
    private CuentaBancariaEmpresa idCuentaBancariaEmpresa;

    @Transient
    @CsvBindByName(column = "\"id_cuenta_bancaria_empresa\"")
    private Integer idCuentaBancariaEmpresa1;

    @ManyToOne
    @JoinColumn(name = "\"idCobro\"", referencedColumnName = "\"id\"", nullable = false)
    private Cobro idCobro;

    @Transient
    @CsvBindByName(column = "\"id_cobro\"")
    private Integer idCobro1;

    @OneToOne(mappedBy = "idPago")
    private DetallePago idDetallePago;

    public Pago() {
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }

    public CuentaBancariaEmpresa getIdCuentaBancariaEmpresa() {
        return idCuentaBancariaEmpresa;
    }

    public void setIdCuentaBancariaEmpresa(CuentaBancariaEmpresa idCuentaBancariaEmpresa) {
        this.idCuentaBancariaEmpresa = idCuentaBancariaEmpresa;
    }

    public Integer getIdCuentaBancariaEmpresa1() {
        return idCuentaBancariaEmpresa1;
    }

    public void setIdCuentaBancariaEmpresa1(Integer idCuentaBancariaEmpresa1) {
        this.idCuentaBancariaEmpresa1 = idCuentaBancariaEmpresa1;
    }

    public Cobro getIdCobro() {
        return idCobro;
    }

    public void setIdCobro(Cobro idCobro) {
        this.idCobro = idCobro;
    }

    public Integer getIdCobro1() {
        return idCobro1;
    }

    public void setIdCobro1(Integer idCobro1) {
        this.idCobro1 = idCobro1;
    }

    public DetallePago getIdDetallePago() {
        return idDetallePago;
    }

    public void setIdDetallePago(DetallePago idDetallePago) {
        this.idDetallePago = idDetallePago;
    }

}
