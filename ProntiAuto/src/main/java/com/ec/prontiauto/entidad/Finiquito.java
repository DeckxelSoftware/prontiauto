package com.ec.prontiauto.entidad;

import com.ec.prontiauto.abstracts.AbstractEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Table(name = "finiquito")
@Entity
public class Finiquito extends AbstractEntities {
    @Column(name = "fecha_salida", nullable = false)
    private Date fechaSalida;

    @Column(name = "motivo_salida", nullable = false)
    private String motivoSalida;

    @Column(name = "valor_vacaciones", nullable = false)
    private Float valor_Vacaciones;

    @Column(name = "decimo_tercero", nullable = false)
    private Float decimoTercero;

    @Column(name = "decimo_cuarto", nullable = false)
    private Float decimoCuarto;

    @Column(name = "aporte_personal_iess", nullable = false)
    private Float aportePersonalIess;

    @Column(name = "desahucio")
    private Float desahucio;

    @Column(name = "despido_intempestivo")
    private Float despidoIntempestivo;

    @Column(name = "prov_iess")
    private Float provIess;

    @Column(name = "total_descuentos" , nullable = false)
    private Float totalDescuentos;

    @Column(name = "credencial")
    private Float credencial;

    @Column(name = "descuento_cliente")
    private Float descuentoCliente;

    @Column(name = "stand")
    private Float stand;

    @Column(name = "chompa")
    private Float chompa;


    @Column(name = "clientes_desistidos")
    private Float clientesDesistidos;

    @Column(name = "valor_a_pagar" , nullable = false)
    private Float valorPagar;

    @Column(name = "responsable" , nullable = false)
    private String responsable;

    @Column(name = "revisor" , nullable = false)
    private String revisor;

    @Column(name = "aprobador" , nullable = false)
    private String aprobador;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador" , referencedColumnName = "id")
    private Trabajador trabajador;

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getMotivoSalida() {
        return motivoSalida;
    }

    public void setMotivoSalida(String motivoSalida) {
        this.motivoSalida = motivoSalida;
    }

    public Float getValor_Vacaciones() {
        return valor_Vacaciones;
    }

    public void setValor_Vacaciones(Float valor_Vacaciones) {
        this.valor_Vacaciones = valor_Vacaciones;
    }

    public Float getDecimoTercero() {
        return decimoTercero;
    }

    public void setDecimoTercero(Float decimoTercero) {
        this.decimoTercero = decimoTercero;
    }

    public Float getDecimoCuarto() {
        return decimoCuarto;
    }

    public void setDecimoCuarto(Float decimoCuarto) {
        this.decimoCuarto = decimoCuarto;
    }

    public Float getAportePersonalIess() {
        return aportePersonalIess;
    }

    public void setAportePersonalIess(Float aportePersonalIess) {
        this.aportePersonalIess = aportePersonalIess;
    }

    public Float getDesahucio() {
        return desahucio;
    }

    public void setDesahucio(Float desahucio) {
        this.desahucio = desahucio;
    }

    public Float getDespidoIntempestivo() {
        return despidoIntempestivo;
    }

    public void setDespidoIntempestivo(Float despidoIntempestivo) {
        this.despidoIntempestivo = despidoIntempestivo;
    }

    public Float getProvIess() {
        return provIess;
    }

    public void setProvIess(Float provIess) {
        this.provIess = provIess;
    }

    public Float getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Float totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Float getCredencial() {
        return credencial;
    }

    public void setCredencial(Float credencial) {
        this.credencial = credencial;
    }

    public Float getDescuentoCliente() {
        return descuentoCliente;
    }

    public void setDescuentoCliente(Float descuentoCliente) {
        this.descuentoCliente = descuentoCliente;
    }

    public Float getStand() {
        return stand;
    }

    public void setStand(Float stand) {
        this.stand = stand;
    }

    public Float getChompa() {
        return chompa;
    }

    public void setChompa(Float chompa) {
        this.chompa = chompa;
    }

    public Float getClientesDesistidos() {
        return clientesDesistidos;
    }

    public void setClientesDesistidos(Float clientesDesistidos) {
        this.clientesDesistidos = clientesDesistidos;
    }

    public Float getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(Float valorPagar) {
        this.valorPagar = valorPagar;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getRevisor() {
        return revisor;
    }

    public void setRevisor(String revisor) {
        this.revisor = revisor;
    }

    public String getAprobador() {
        return aprobador;
    }

    public void setAprobador(String aprobador) {
        this.aprobador = aprobador;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
}
