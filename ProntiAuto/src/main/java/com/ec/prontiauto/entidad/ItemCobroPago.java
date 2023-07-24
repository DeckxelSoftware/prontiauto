package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "item_cobro_pago")
public class ItemCobroPago extends AbstractEntities {

    @Column(name = "nombre_item", nullable = false)
    @CsvBindByName(column = "nombre_item")
    private String nombreItem;

    @Column(name = "nombre_cuenta", nullable = false)
    @CsvBindByName(column = "nombre_cuenta")
    private String nombreCuenta;

    @ManyToOne
    @JoinColumn(name = "\"idCuentaContable\"", referencedColumnName = "id", nullable = false)
    private CuentaContable idCuentaContable;

    @Transient
    @CsvBindByName(column = "id_cuenta_contable")
    private Integer idCuentaContable1;

    @OneToMany(mappedBy = "idItemCobroPago")
    private List<DetallePago> detallePagoCollection;

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public CuentaContable getIdCuentaContable() {
        return idCuentaContable;
    }

    public void setIdCuentaContable(CuentaContable idCuentaContable) {
        this.idCuentaContable = idCuentaContable;
    }

    public Integer getIdCuentaContable1() {
        return idCuentaContable1;
    }

    public void setIdCuentaContable1(Integer idCuentaContable1) {
        this.idCuentaContable1 = idCuentaContable1;
    }

    public ItemCobroPago setValoresDiferentes(ItemCobroPago registroAntiguo, ItemCobroPago registroActualizado) {

        if (registroActualizado.getNombreItem() != null) {
            this.nombreItem = registroActualizado.getNombreItem();
        }

        if (registroActualizado.getNombreCuenta() != null) {
            this.nombreCuenta = registroActualizado.getNombreCuenta();
        }

        return registroAntiguo;
    }

    public List<DetallePago> getDetallePagoCollection() {
        return detallePagoCollection;
    }

    public void setDetallePagoCollection(List<DetallePago> detallePagoCollection) {
        this.detallePagoCollection = detallePagoCollection;
    }

}
