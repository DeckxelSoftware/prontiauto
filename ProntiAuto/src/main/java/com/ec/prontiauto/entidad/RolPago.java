package com.ec.prontiauto.entidad;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "rol_pago", uniqueConstraints = @UniqueConstraint(columnNames = { "\"idHistorialLaboral\"", "\"idPeriodoLaboral\"" }))
public class RolPago extends AbstractEntities {

    @Column(name = "horas25", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "horas_25")
    private Float horas25;

    @Column(name = "horas50", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "horas_50")
    private Float horas50;

    @Column(name = "horas100", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "horas_100")
    private Float horas100;

    @Column(name = "\"retencionesJudiciales\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "retenciones_judiciales")
    private Float retencionesJudiciales;

    @Column(name = "\"polizaPersonal\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "poliza_personal")
    private Float polizaPersonal;

    @Column(name = "\"aporteIess\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "aporte_iess")
    private Float aporteIess;

    @Column(name = "\"impuestoRenta\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "impuesto_renta")
    private Float impuestoRenta;

    @Column(name = "\"descuentosPorAtrasos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "descuentos_por_atrasos")
    private Float descuentosPorAtrasos;

    @Column(name = "\"Anticipos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "anticipos")
    private Float Anticipos;

    @Column(name = "multas", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "multas")
    private Float multas;

    @Column(name = "\"otrosDescuentos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "otros_descuentos")
    private Float otrosDescuentos;

    @Column(name = "\"celularConsumo\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "celular_consumo")
    private Float celularConsumo;

    @Column(name = "\"permisoHoras\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "permiso_horas")
    private Float permisoHoras;

    @Column(name = "\"permisoDias\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "permiso_dias")
    private Float permisoDias;

    @Column(name = "\"descuentosPorFaltas\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "descuentos_por_faltas")
    private Float descuentosPorFaltas;

    @Column(name = "\"leySolidaria\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "ley_solidaria")
    private Float leySolidaria;

    @Column(name = "\"prestamosEmpresa\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prestamos_empresa")
    private Float prestamosEmpresa;

    @Column(name = "peluqueria", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "peluqueria")
    private Float peluqueria;

    @Column(name = "optica", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "optica")
    private Float optica;

    @Column(name = "\"pagoCuotaVehiculo\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "pago_cuota_vehiculo")
    private Float pagoCuotaVehiculo;

    @Column(name = "sueldo", nullable = false, columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "sueldo")
    private Float sueldo;

    @Column(name = "bonificacion", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "bonificacion")
    private Float bonificacion;

    @Column(name = "\"movilizacionEspecial\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "movilizacion_especial")
    private Float movilizacionEspecial;

    @Column(name = "\"componenteSalarialUnif\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "componente_salarial_unif")
    private Float componenteSalarialUnif;

    @Column(name = "\"diasFalta\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "dias_falta")
    private Float diasFalta;

    @Column(name = "\"diasMaternidad\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "dias_maternidad")
    private Float diasMaternidad;

    @Column(name = "\"diasEnfermedad\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "dias_enfermedad")
    private Float diasEnfermedad;

    @Column(name = "\"diasVacacion\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "dias_vacacion")
    private Float diasVacacion;

    @Column(name = "\"horasAtraso\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "horas_atraso")
    private Float horasAtraso;

    @Column(name = "\"descuentoMaternidad\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "descuento_maternidad")
    private Float descuentoMaternidad;

    @Column(name = "\"descuentoEnfermedad\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "descuento_enfermedad")
    private Float descuentoEnfermedad;

    @Column(name = "\"descuentoVacacion\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "descuento_vacacion")
    private Float descuentoVacacion;

    @Column(name = "\"ProvDecimoTercero\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prov_decimo_tercero")
    private Float ProvDecimoTercero;

    @Column(name = "\"ProvDecimoCuarto\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prov_decimo_cuarto")
    private Float ProvDecimoCuarto;

    @Column(name = "\"provFondosReserva\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prov_fondos_reserva")
    private Float provFondosReserva;

    @Column(name = "\"provVacaciones\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prov_vacaciones")
    private Float provVacaciones;

    @Column(name = "\"provAportePatronal\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prov_aporte_patronal")
    private Float provAportePatronal;

    @Column(name = "vacacion1", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "vacacion1")
    private Float vacacion1;

    @Column(name = "vacacion2", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "vacacion2")
    private Float vacacion2;

    @Column(name = "\"pagoVacacion\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "pago_vacacion")
    private Float pagoVacacion;

    @Column(name = "\"pagoFondoReservaMes\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "pago_fondo_reserva_mes")
    private Float pagoFondoReservaMes;

    @Column(name = "\"pagoDecimoTerceroCuartoMes\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "pago_decimo_tercero_cuarto_mes")
    private Float pagoDecimoTerceroCuartoMes;

    @Column(name = "\"acumuladoAportePersonalIess\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "acumulado_aporte_personal_iess")
    private Float acumuladoAportePersonalIess;

    @Column(name = "\"acumuladoDecimoTercero\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "acumulado_decimo_tercero")
    private Float acumuladoDecimoTercero;

    @Column(name = "\"acumuladoFondosReserva\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "acumulado_fondos_reserva")
    private Float acumuladoFondosReserva;

    @Column(name = "\"acumuladoVacaciones\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "acumulado_vacaciones")
    private Float acumuladoVacaciones;

    @Column(name = "\"acumuladoImpuestoRenta\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "acumulado_impuesto_renta")
    private Float acumuladoImpuestoRenta;

    @Column(name = "\"totalIngresos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "total_ingresos")
    private Float totalIngresos;

    @Column(name = "\"totalEgresos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "total_egresos")
    private Float totalEgresos;

    @Column(name = "\"totalAPagar\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "total_a_pagar")
    private Float totalAPagar;

    @Column(name = "\"otrosIngresos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "otros_ingresos")
    private Float otrosIngresos;

    @Column(name = "\"prestamosIess\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prestamos_iess")
    private Float prestamosIess;

    @Column(name = "\"totalHorasExtra\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "total_horas_extra")
    private Float totalHorasExtra;

    @Column(name = "\"fechaDesde\"")
    @CsvBindByName(column = "fecha_desde")
    private Date fechaDesde;

    @Column(name = "\"rolCerrado\"")
    @CsvBindByName(column = "rol_cerrado")
    private String rolCerrado;

    @Column(name = "comision")
    @CsvBindByName(column = "comision")
    private Float comision;

    @Column(name = "\"ajusteIngreso\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "ajuste_ingreso")
    private Float ajusteIngreso;

    @Column(name = "\"ajusteEgreso\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "ajuste_egreso")
    private Float ajusteEgreso;

    @Column(name = "\"incentivosOcacionales\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "incentivos_ocacionales")
    private Float incentivosOcacionales;

    @Column(name = "retroactivo", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "retroactivo")
    private Float retroactivo;

    @Column(name = "\"prestamoQuirografario\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prestamo_quirografario")
    private Float prestamoQuirografario;

    @Column(name = "\"prestamoHipotecario\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prestamo_hipotecario")
    private Float prestamoHipotecario;

    @Column(name = "\"pagoDecimoCuartoMes\"")
    @CsvBindByName(column = "pago_decimo_cuarto_mes")
    private Float pagoDecimoCuartoMes;

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

    public Float getAjusteIngreso() {
        return ajusteIngreso;
    }

    public void setAjusteIngreso(Float ajusteIngreso) {
        this.ajusteIngreso = ajusteIngreso;
    }

    public Float getAjusteEgreso() {
        return ajusteEgreso;
    }

    public void setAjusteEgreso(Float ajusteEgreso) {
        this.ajusteEgreso = ajusteEgreso;
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

    @Column(name = "\"fechaHasta\"")
    @CsvBindByName(column = "fecha_hasta")
    private Date fechaHasta;

    @ManyToOne
    @JoinColumn(name = "\"idPeriodoLaboral\"", referencedColumnName = "id", nullable = false)
    private PeriodoLaboral idPeriodoLaboral;

    @ManyToOne
    @JoinColumn(name = "\"idHistorialLaboral\"", referencedColumnName = "id", nullable = false)
    private HistorialLaboral idHistorialLaboral;


    @Transient
    @CsvBindByName(column = "id_periodo_laboral")
    private Integer idPeriodoLaboral1;

    @Transient
    @CsvBindByName(column = "id_historial_laboral")
    private Integer idHistorialLaboral1;

    public Float getHoras25() {
        return horas25;
    }

    public void setHoras25(Float horas25) {
        this.horas25 = horas25;
    }

    public Float getHoras50() {
        return horas50;
    }

    public void setHoras50(Float horas50) {
        this.horas50 = horas50;
    }

    public Float getHoras100() {
        return horas100;
    }

    public void setHoras100(Float horas100) {
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
        return Anticipos;
    }

    public void setAnticipos(Float anticipos) {
        Anticipos = anticipos;
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
        return ProvDecimoTercero;
    }

    public void setProvDecimoTercero(Float provDecimoTercero) {
        ProvDecimoTercero = provDecimoTercero;
    }

    public Float getProvDecimoCuarto() {
        return ProvDecimoCuarto;
    }

    public void setProvDecimoCuarto(Float provDecimoCuarto) {
        ProvDecimoCuarto = provDecimoCuarto;
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

    public PeriodoLaboral getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(PeriodoLaboral idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }

    public HistorialLaboral getIdHistorialLaboral() {
        return idHistorialLaboral;
    }

    public void setIdHistorialLaboral(HistorialLaboral idHistorialLaboral) {
        this.idHistorialLaboral = idHistorialLaboral;
    }

    public Integer getIdPeriodoLaboral1() {
        return idPeriodoLaboral1;
    }

    public void setIdPeriodoLaboral1(Integer idPeriodoLaboral1) {
        this.idPeriodoLaboral1 = idPeriodoLaboral1;
    }

    public Integer getIdHistorialLaboral1() {
        return idHistorialLaboral1;
    }

    public void setIdHistorialLaboral1(Integer idHistorialLaboral1) {
        this.idHistorialLaboral1 = idHistorialLaboral1;
    }

    public Float getPagoDecimoCuartoMes() {
        return pagoDecimoCuartoMes;
    }

    public void setPagoDecimoCuartoMes(Float pagoDecimoCuartoMes) {
        this.pagoDecimoCuartoMes = pagoDecimoCuartoMes;
    }

    public RolPago setValoresDiferentes(RolPago registroAntiguo, RolPago registroActualizado) {

        if (registroActualizado.getHoras25() != null) {
            registroAntiguo.setHoras25(registroActualizado.getHoras25());
        }

        if (registroActualizado.getHoras50() != null) {
            registroAntiguo.setHoras50(registroActualizado.getHoras50());
        }

        if (registroActualizado.getHoras100() != null) {
            registroAntiguo.setHoras100(registroActualizado.getHoras100());
        }

        if (registroActualizado.getRetencionesJudiciales() != null) {
            registroAntiguo.setRetencionesJudiciales(registroActualizado.getRetencionesJudiciales());
        }

        if (registroActualizado.getPolizaPersonal() != null) {
            registroAntiguo.setPolizaPersonal(registroActualizado.getPolizaPersonal());
        }

        if (registroActualizado.getAporteIess() != null) {
            registroAntiguo.setAporteIess(registroActualizado.getAporteIess());
        }

        if (registroActualizado.getImpuestoRenta() != null) {
            registroAntiguo.setImpuestoRenta(registroActualizado.getImpuestoRenta());
        }

        if (registroActualizado.getDescuentosPorAtrasos() != null) {
            registroAntiguo.setDescuentosPorAtrasos(registroActualizado.getDescuentosPorAtrasos());
        }

        if (registroActualizado.getAnticipos() != null) {
            registroAntiguo.setAnticipos(registroActualizado.getAnticipos());
        }

        if (registroActualizado.getMultas() != null) {
            registroAntiguo.setMultas(registroActualizado.getMultas());
        }

        if (registroActualizado.getOtrosDescuentos() != null) {
            registroAntiguo.setOtrosDescuentos(registroActualizado.getOtrosDescuentos());
        }

        if (registroActualizado.getCelularConsumo() != null) {
            registroAntiguo.setCelularConsumo(registroActualizado.getCelularConsumo());
        }

        if (registroActualizado.getPermisoHoras() != null) {
            registroAntiguo.setPermisoHoras(registroActualizado.getPermisoHoras());
        }

        if (registroActualizado.getPermisoDias() != null) {
            registroAntiguo.setPermisoDias(registroActualizado.getPermisoDias());
        }

        if (registroActualizado.getDescuentosPorFaltas() != null) {
            registroAntiguo.setDescuentosPorFaltas(registroActualizado.getDescuentosPorFaltas());
        }

        if (registroActualizado.getLeySolidaria() != null) {
            registroAntiguo.setLeySolidaria(registroActualizado.getLeySolidaria());
        }

        if (registroActualizado.getPrestamosEmpresa() != null) {
            registroAntiguo.setPrestamosEmpresa(registroActualizado.getPrestamosEmpresa());
        }

        if (registroActualizado.getPeluqueria() != null) {
            registroAntiguo.setPeluqueria(registroActualizado.getPeluqueria());
        }

        if (registroActualizado.getOptica() != null) {
            registroAntiguo.setOptica(registroActualizado.getOptica());
        }

        if (registroActualizado.getPagoCuotaVehiculo() != null) {
            registroAntiguo.setPagoCuotaVehiculo(registroActualizado.getPagoCuotaVehiculo());
        }

        if (registroActualizado.getSueldo() != null) {
            registroAntiguo.setSueldo(registroActualizado.getSueldo());
        }

        if (registroActualizado.getBonificacion() != null) {
            registroAntiguo.setBonificacion(registroActualizado.getBonificacion());
        }

        if (registroActualizado.getMovilizacionEspecial() != null) {
            registroAntiguo.setMovilizacionEspecial(registroActualizado.getMovilizacionEspecial());
        }

        if (registroActualizado.getComponenteSalarialUnif() != null) {
            registroAntiguo.setComponenteSalarialUnif(registroActualizado.getComponenteSalarialUnif());
        }

        if (registroActualizado.getDiasFalta() != null) {
            registroAntiguo.setDiasFalta(registroActualizado.getDiasFalta());
        }

        if (registroActualizado.getDiasMaternidad() != null) {
            registroAntiguo.setDiasMaternidad(registroActualizado.getDiasMaternidad());
        }

        if (registroActualizado.getDiasEnfermedad() != null) {
            registroAntiguo.setDiasEnfermedad(registroActualizado.getDiasEnfermedad());
        }

        if (registroActualizado.getDiasVacacion() != null) {
            registroAntiguo.setDiasVacacion(registroActualizado.getDiasVacacion());
        }

        if (registroActualizado.getHorasAtraso() != null) {
            registroAntiguo.setHorasAtraso(registroActualizado.getHorasAtraso());
        }

        if (registroActualizado.getDescuentoMaternidad() != null) {
            registroAntiguo.setDescuentoMaternidad(registroActualizado.getDescuentoMaternidad());
        }

        if (registroActualizado.getDescuentoEnfermedad() != null) {
            registroAntiguo.setDescuentoEnfermedad(registroActualizado.getDescuentoEnfermedad());
        }

        if (registroActualizado.getDescuentoVacacion() != null) {
            registroAntiguo.setDescuentoVacacion(registroActualizado.getDescuentoVacacion());
        }

        if (registroActualizado.getProvDecimoTercero() != null) {
            registroAntiguo.setProvDecimoTercero(registroActualizado.getProvDecimoTercero());
        }

        if (registroActualizado.getProvDecimoCuarto() != null) {
            registroAntiguo.setProvDecimoCuarto(registroActualizado.getProvDecimoCuarto());
        }

        if (registroActualizado.getProvFondosReserva() != null) {
            registroAntiguo.setProvFondosReserva(registroActualizado.getProvFondosReserva());
        }

        if (registroActualizado.getProvVacaciones() != null) {
            registroAntiguo.setProvVacaciones(registroActualizado.getProvVacaciones());
        }

        if (registroActualizado.getProvAportePatronal() != null) {
            registroAntiguo.setProvAportePatronal(registroActualizado.getProvAportePatronal());
        }

        if (registroActualizado.getVacacion1() != null) {
            registroAntiguo.setVacacion1(registroActualizado.getVacacion1());
        }

        if (registroActualizado.getVacacion2() != null) {
            registroAntiguo.setVacacion2(registroActualizado.getVacacion2());
        }

        if (registroActualizado.getPagoVacacion() != null) {
            registroAntiguo.setPagoVacacion(registroActualizado.getPagoVacacion());
        }

        if (registroActualizado.getPagoFondoReservaMes() != null) {
            registroAntiguo.setPagoFondoReservaMes(registroActualizado.getPagoFondoReservaMes());
        }

        if (registroActualizado.getPagoDecimoTerceroCuartoMes() != null) {
            registroAntiguo.setPagoDecimoTerceroCuartoMes(registroActualizado.getPagoDecimoTerceroCuartoMes());
        }

        if (registroActualizado.getAcumuladoAportePersonalIess() != null) {
            registroAntiguo.setAcumuladoAportePersonalIess(registroActualizado.getAcumuladoAportePersonalIess());
        }

        if (registroActualizado.getAcumuladoDecimoTercero() != null) {
            registroAntiguo.setAcumuladoDecimoTercero(registroActualizado.getAcumuladoDecimoTercero());
        }

        if (registroActualizado.getAcumuladoFondosReserva() != null) {
            registroAntiguo.setAcumuladoFondosReserva(registroActualizado.getAcumuladoFondosReserva());
        }

        if (registroActualizado.getAcumuladoVacaciones() != null) {
            registroAntiguo.setAcumuladoVacaciones(registroActualizado.getAcumuladoVacaciones());
        }

        if (registroActualizado.getAcumuladoImpuestoRenta() != null) {
            registroAntiguo.setAcumuladoImpuestoRenta(registroActualizado.getAcumuladoImpuestoRenta());
        }

        if (registroActualizado.getTotalIngresos() != null) {
            registroAntiguo.setTotalIngresos(registroActualizado.getTotalIngresos());
        }

        if (registroActualizado.getTotalEgresos() != null) {
            registroAntiguo.setTotalEgresos(registroActualizado.getTotalEgresos());
        }

        if (registroActualizado.getTotalAPagar() != null) {
            registroAntiguo.setTotalAPagar(registroActualizado.getTotalAPagar());
        }

        if (registroActualizado.getOtrosIngresos() != null) {
            registroAntiguo.setOtrosIngresos(registroActualizado.getOtrosIngresos());
        }

        if (registroActualizado.getPrestamosIess() != null) {
            registroAntiguo.setPrestamosIess(registroActualizado.getPrestamosIess());
        }

        if (registroActualizado.getTotalHorasExtra() != null) {
            registroAntiguo.setTotalHorasExtra(registroActualizado.getTotalHorasExtra());
        }

        if (registroActualizado.getFechaDesde() != null) {
            registroAntiguo.setFechaDesde(registroActualizado.getFechaDesde());
        }

        if (registroActualizado.getFechaHasta() != null) {
            registroAntiguo.setFechaHasta(registroActualizado.getFechaHasta());
        }

        if (registroActualizado.getRolCerrado() != null) {
            registroAntiguo.setRolCerrado(registroActualizado.getRolCerrado());
        }

        if (registroActualizado.getComision() != null) {
            registroAntiguo.setComision(registroActualizado.getComision());
        }

        if (registroActualizado.getAjusteIngreso() != null) {
            registroAntiguo.setAjusteIngreso(registroActualizado.getAjusteIngreso());
        }

        if (registroActualizado.getAjusteEgreso() != null) {
            registroAntiguo.setAjusteEgreso(registroActualizado.getAjusteEgreso());
        }

        if (registroActualizado.getIncentivosOcacionales() != null) {
            registroAntiguo.setIncentivosOcacionales(registroActualizado.getIncentivosOcacionales());
        }

        if (registroActualizado.getRetroactivo() != null) {
            registroAntiguo.setRetroactivo(registroActualizado.getRetroactivo());
        }

        if (registroActualizado.getPrestamoQuirografario() != null) {
            registroAntiguo.setPrestamoQuirografario(registroActualizado.getPrestamoQuirografario());
        }

        if (registroActualizado.getPrestamoHipotecario() != null) {
            registroAntiguo.setPrestamoHipotecario(registroActualizado.getPrestamoHipotecario());
        }

        if (registroActualizado.getPagoDecimoCuartoMes() != null){
            registroAntiguo.setPagoDecimoCuartoMes(registroActualizado.getPagoDecimoCuartoMes());
        }

        if(registroActualizado.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroActualizado.getSisHabilitado());
        }

        return registroAntiguo;
    }

}
