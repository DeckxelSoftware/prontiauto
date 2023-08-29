package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

import java.sql.Date;

public class HistoricoRolResponseDao extends AbstractResponseDao {
    private Integer horas25;
    private Integer horas50;
    private Integer horas100;
    private Float retencionesJudiciales;
    private Float polizaPersonal;
    private Float aporteIess;
    private Float impuestoRenta;
    private Float descuentosPorAtrasos;
    private Float anticipos;
    private Float multas;
    private Float otrosDescuentos;
    private Float celularConsumo;
    private Float permisoHoras;
    private Float permisoDias;
    private Float descuentosPorFaltas;
    private Float leySolidaria;
    private Float prestamosEmpresa;
    private Float peluqueria;
    private Float optica;
    private Float pagoCuotaVehiculo;
    private Float sueldo;
    private Float bonificacion;
    private Float movilizacionEspecial;
    private Float componenteSalarialUnif;
    private Float diasFalta;
    private Float diasMaternidad;
    private Float diasEnfermedad;
    private Float diasVacacion;
    private Float horasAtraso;
    private Float descuentoMaternidad;
    private Float descuentoEnfermedad;
    private Float descuentoVacacion;
    private Float provDecimoTercero;
    private Float provDecimoCuarto;
    private Float provFondosReserva;
    private Float provVacaciones;
    private Float provAportePatronal;
    private Float vacacion1;
    private Float vacacion2;
    private Float pagoVacacion;
    private Float pagoFondoReservaMes;
    private Float pagoDecimoTerceroCuartoMes;
    private Float acumuladoAportePersonalIess;
    private Float acumuladoDecimoTercero;
    private Float acumuladoFondosReserva;
    private Float acumuladoVacaciones;
    private Float acumuladoImpuestoRenta;
    private Float totalIngresos;
    private Float totalEgresos;
    private Float totalAPagar;
    private Float otrosIngresos;
    private Float prestamosIess;
    private Float totalHorasExtra;
    private Date fechaDesde;
    private Date fechaHasta;
    private String rolCerrado;
    private Float comision;
    private Float ajustesIngreso;
    private Float ajustesEgreso;
    private Float incentivosOcacionales;
    private Float retroactivo;
    private Float prestamoQuirografario;
    private Float prestamoHipotecario;
    private Float pagoDecimoCuartoMes;

    public Integer getHoras25() {
        return horas25;
    }

    public void setHoras25(Integer horas25) {
        this.horas25 = horas25;
    }

    public Integer getHoras50() {
        return horas50;
    }

    public void setHoras50(Integer horas50) {
        this.horas50 = horas50;
    }

    public Integer getHoras100() {
        return horas100;
    }

    public void setHoras100(Integer horas100) {
        this.horas100 = horas100;
    }

    public Float getRetencionesJudiciales() {
        return retencionesJudiciales;
    }

    public void setRetencionesJudiciales(Float retencionesJudiciales) {
        this.retencionesJudiciales = retencionesJudiciales;
    }

    public Float getPolizaPersonal() {
        return polizaPersonal;
    }

    public void setPolizaPersonal(Float polizaPersonal) {
        this.polizaPersonal = polizaPersonal;
    }

    public Float getAporteIess() {
        return aporteIess;
    }

    public void setAporteIess(Float aporteIess) {
        this.aporteIess = aporteIess;
    }

    public Float getImpuestoRenta() {
        return impuestoRenta;
    }

    public void setImpuestoRenta(Float impuestoRenta) {
        this.impuestoRenta = impuestoRenta;
    }

    public Float getDescuentosPorAtrasos() {
        return descuentosPorAtrasos;
    }

    public void setDescuentosPorAtrasos(Float descuentosPorAtrasos) {
        this.descuentosPorAtrasos = descuentosPorAtrasos;
    }

    public Float getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(Float anticipos) {
        this.anticipos = anticipos;
    }

    public Float getMultas() {
        return multas;
    }

    public void setMultas(Float multas) {
        this.multas = multas;
    }

    public Float getOtrosDescuentos() {
        return otrosDescuentos;
    }

    public void setOtrosDescuentos(Float otrosDescuentos) {
        this.otrosDescuentos = otrosDescuentos;
    }

    public Float getCelularConsumo() {
        return celularConsumo;
    }

    public void setCelularConsumo(Float celularConsumo) {
        this.celularConsumo = celularConsumo;
    }

    public Float getPermisoHoras() {
        return permisoHoras;
    }

    public void setPermisoHoras(Float permisoHoras) {
        this.permisoHoras = permisoHoras;
    }

    public Float getPermisoDias() {
        return permisoDias;
    }

    public void setPermisoDias(Float permisoDias) {
        this.permisoDias = permisoDias;
    }

    public Float getDescuentosPorFaltas() {
        return descuentosPorFaltas;
    }

    public void setDescuentosPorFaltas(Float descuentosPorFaltas) {
        this.descuentosPorFaltas = descuentosPorFaltas;
    }

    public Float getLeySolidaria() {
        return leySolidaria;
    }

    public void setLeySolidaria(Float leySolidaria) {
        this.leySolidaria = leySolidaria;
    }

    public Float getPrestamosEmpresa() {
        return prestamosEmpresa;
    }

    public void setPrestamosEmpresa(Float prestamosEmpresa) {
        this.prestamosEmpresa = prestamosEmpresa;
    }

    public Float getPeluqueria() {
        return peluqueria;
    }

    public void setPeluqueria(Float peluqueria) {
        this.peluqueria = peluqueria;
    }

    public Float getOptica() {
        return optica;
    }

    public void setOptica(Float optica) {
        this.optica = optica;
    }

    public Float getPagoCuotaVehiculo() {
        return pagoCuotaVehiculo;
    }

    public void setPagoCuotaVehiculo(Float pagoCuotaVehiculo) {
        this.pagoCuotaVehiculo = pagoCuotaVehiculo;
    }

    public Float getSueldo() {
        return sueldo;
    }

    public void setSueldo(Float sueldo) {
        this.sueldo = sueldo;
    }

    public Float getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(Float bonificacion) {
        this.bonificacion = bonificacion;
    }

    public Float getMovilizacionEspecial() {
        return movilizacionEspecial;
    }

    public void setMovilizacionEspecial(Float movilizacionEspecial) {
        this.movilizacionEspecial = movilizacionEspecial;
    }

    public Float getComponenteSalarialUnif() {
        return componenteSalarialUnif;
    }

    public void setComponenteSalarialUnif(Float componenteSalarialUnif) {
        this.componenteSalarialUnif = componenteSalarialUnif;
    }

    public Float getDiasFalta() {
        return diasFalta;
    }

    public void setDiasFalta(Float diasFalta) {
        this.diasFalta = diasFalta;
    }

    public Float getDiasMaternidad() {
        return diasMaternidad;
    }

    public void setDiasMaternidad(Float diasMaternidad) {
        this.diasMaternidad = diasMaternidad;
    }

    public Float getDiasEnfermedad() {
        return diasEnfermedad;
    }

    public void setDiasEnfermedad(Float diasEnfermedad) {
        this.diasEnfermedad = diasEnfermedad;
    }

    public Float getDiasVacacion() {
        return diasVacacion;
    }

    public void setDiasVacacion(Float diasVacacion) {
        this.diasVacacion = diasVacacion;
    }

    public Float getHorasAtraso() {
        return horasAtraso;
    }

    public void setHorasAtraso(Float horasAtraso) {
        this.horasAtraso = horasAtraso;
    }

    public Float getDescuentoMaternidad() {
        return descuentoMaternidad;
    }

    public void setDescuentoMaternidad(Float descuentoMaternidad) {
        this.descuentoMaternidad = descuentoMaternidad;
    }

    public Float getDescuentoEnfermedad() {
        return descuentoEnfermedad;
    }

    public void setDescuentoEnfermedad(Float descuentoEnfermedad) {
        this.descuentoEnfermedad = descuentoEnfermedad;
    }

    public Float getDescuentoVacacion() {
        return descuentoVacacion;
    }

    public void setDescuentoVacacion(Float descuentoVacacion) {
        this.descuentoVacacion = descuentoVacacion;
    }

    public Float getProvDecimoTercero() {
        return provDecimoTercero;
    }

    public void setProvDecimoTercero(Float provDecimoTercero) {
        this.provDecimoTercero = provDecimoTercero;
    }

    public Float getProvDecimoCuarto() {
        return provDecimoCuarto;
    }

    public void setProvDecimoCuarto(Float provDecimoCuarto) {
        this.provDecimoCuarto = provDecimoCuarto;
    }

    public Float getProvFondosReserva() {
        return provFondosReserva;
    }

    public void setProvFondosReserva(Float provFondosReserva) {
        this.provFondosReserva = provFondosReserva;
    }

    public Float getProvVacaciones() {
        return provVacaciones;
    }

    public void setProvVacaciones(Float provVacaciones) {
        this.provVacaciones = provVacaciones;
    }

    public Float getProvAportePatronal() {
        return provAportePatronal;
    }

    public void setProvAportePatronal(Float provAportePatronal) {
        this.provAportePatronal = provAportePatronal;
    }

    public Float getVacacion1() {
        return vacacion1;
    }

    public void setVacacion1(Float vacacion1) {
        this.vacacion1 = vacacion1;
    }

    public Float getVacacion2() {
        return vacacion2;
    }

    public void setVacacion2(Float vacacion2) {
        this.vacacion2 = vacacion2;
    }

    public Float getPagoVacacion() {
        return pagoVacacion;
    }

    public void setPagoVacacion(Float pagoVacacion) {
        this.pagoVacacion = pagoVacacion;
    }

    public Float getPagoFondoReservaMes() {
        return pagoFondoReservaMes;
    }

    public void setPagoFondoReservaMes(Float pagoFondoReservaMes) {
        this.pagoFondoReservaMes = pagoFondoReservaMes;
    }

    public Float getPagoDecimoTerceroCuartoMes() {
        return pagoDecimoTerceroCuartoMes;
    }

    public void setPagoDecimoTerceroCuartoMes(Float pagoDecimoTerceroCuartoMes) {
        this.pagoDecimoTerceroCuartoMes = pagoDecimoTerceroCuartoMes;
    }

    public Float getAcumuladoAportePersonalIess() {
        return acumuladoAportePersonalIess;
    }

    public void setAcumuladoAportePersonalIess(Float acumuladoAportePersonalIess) {
        this.acumuladoAportePersonalIess = acumuladoAportePersonalIess;
    }

    public Float getAcumuladoDecimoTercero() {
        return acumuladoDecimoTercero;
    }

    public void setAcumuladoDecimoTercero(Float acumuladoDecimoTercero) {
        this.acumuladoDecimoTercero = acumuladoDecimoTercero;
    }

    public Float getAcumuladoFondosReserva() {
        return acumuladoFondosReserva;
    }

    public void setAcumuladoFondosReserva(Float acumuladoFondosReserva) {
        this.acumuladoFondosReserva = acumuladoFondosReserva;
    }

    public Float getAcumuladoVacaciones() {
        return acumuladoVacaciones;
    }

    public void setAcumuladoVacaciones(Float acumuladoVacaciones) {
        this.acumuladoVacaciones = acumuladoVacaciones;
    }

    public Float getAcumuladoImpuestoRenta() {
        return acumuladoImpuestoRenta;
    }

    public void setAcumuladoImpuestoRenta(Float acumuladoImpuestoRenta) {
        this.acumuladoImpuestoRenta = acumuladoImpuestoRenta;
    }

    public Float getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Float totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public Float getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(Float totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    public Float getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(Float totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Float getOtrosIngresos() {
        return otrosIngresos;
    }

    public void setOtrosIngresos(Float otrosIngresos) {
        this.otrosIngresos = otrosIngresos;
    }

    public Float getPrestamosIess() {
        return prestamosIess;
    }

    public void setPrestamosIess(Float prestamosIess) {
        this.prestamosIess = prestamosIess;
    }

    public Float getTotalHorasExtra() {
        return totalHorasExtra;
    }

    public void setTotalHorasExtra(Float totalHorasExtra) {
        this.totalHorasExtra = totalHorasExtra;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getRolCerrado() {
        return rolCerrado;
    }

    public void setRolCerrado(String rolCerrado) {
        this.rolCerrado = rolCerrado;
    }

    public Float getComision() {
        return comision;
    }

    public void setComision(Float comision) {
        this.comision = comision;
    }

    public Float getAjustesIngreso() {
        return ajustesIngreso;
    }

    public void setAjustesIngreso(Float ajustesIngreso) {
        this.ajustesIngreso = ajustesIngreso;
    }

    public Float getAjustesEgreso() {
        return ajustesEgreso;
    }

    public void setAjustesEgreso(Float ajustesEgreso) {
        this.ajustesEgreso = ajustesEgreso;
    }

    public Float getIncentivosOcacionales() {
        return incentivosOcacionales;
    }

    public void setIncentivosOcacionales(Float incentivosOcacionales) {
        this.incentivosOcacionales = incentivosOcacionales;
    }

    public Float getRetroactivo() {
        return retroactivo;
    }

    public void setRetroactivo(Float retroactivo) {
        this.retroactivo = retroactivo;
    }

    public Float getPrestamoQuirografario() {
        return prestamoQuirografario;
    }

    public void setPrestamoQuirografario(Float prestamoQuirografario) {
        this.prestamoQuirografario = prestamoQuirografario;
    }

    public Float getPrestamoHipotecario() {
        return prestamoHipotecario;
    }

    public void setPrestamoHipotecario(Float prestamoHipotecario) {
        this.prestamoHipotecario = prestamoHipotecario;
    }

    public Float getPagoDecimoCuartoMes() {
        return pagoDecimoCuartoMes;
    }

    public void setPagoDecimoCuartoMes(Float pagoDecimoCuartoMes) {
        this.pagoDecimoCuartoMes = pagoDecimoCuartoMes;
    }

}
