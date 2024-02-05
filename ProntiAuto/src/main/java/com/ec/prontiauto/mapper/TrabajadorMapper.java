package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.TrabajadorRequestDao;
import com.ec.prontiauto.dao.TrabajadorResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.entidad.Usuario;

public class TrabajadorMapper {
    public static Function<TrabajadorRequestDao, Trabajador> setDaoRequestToEntity;
    public static Function<Trabajador, TrabajadorResponseDao> setEntityToDaoResponse;
    public static Function<List<Trabajador>, List<TrabajadorResponseDao>> setEntityListToDaoResponseList;
    public static Function<Trabajador, TrabajadorResponseDao> setEntityToDaoReference;
    public static Function<Trabajador, TrabajadorResponseDao> setEntityToDaoReferenceUsario;
    public static Function<List<Trabajador>, List<TrabajadorResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Trabajador entity = new Trabajador();
            entity.setId(daoRequest.getId());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setEstadoCivil(daoRequest.getEstadoCivil());
            entity.setGenero(daoRequest.getGenero());
            entity.setGrupoSanguineo(daoRequest.getGrupoSanguineo());
            entity.setNivelEstudios(daoRequest.getNivelEstudios());
            entity.setProfesion(daoRequest.getProfesion());
            entity.setDireccionDomiciliaria(daoRequest.getDireccionDomiciliaria());
            entity.setEstadoFamiliar(daoRequest.getEstadoFamiliar());
            entity.setNumeroAfiliacion(daoRequest.getNumeroAfiliacion());
            entity.setDiscapacidad(daoRequest.getDiscapacidad());
            entity.setTipoDiscapacidad(daoRequest.getTipoDiscapacidad());
            entity.setSueldo(daoRequest.getSueldo());
            entity.setBonificacion(daoRequest.getBonificacion());
            entity.setMovilizacionEspecial(daoRequest.getMovilizacionEspecial());
            entity.setComponenteSalarialUnifi(daoRequest.getComponenteSalarialUnifi());
            entity.setRetencionesJudiciales(daoRequest.getRetencionesJudiciales());
            entity.setPolizaPersonal(daoRequest.getPolizaPersonal());
            entity.setAporteIess(daoRequest.getAporteIess());
            entity.setDecimosAnio(daoRequest.getDecimosAnio());
            entity.setImpuestoRenta(daoRequest.getImpuestoRenta());
            entity.setFondoReservaIess(daoRequest.getFondoReservaIess());
            entity.setUtilidades(daoRequest.getUtilidades());
            entity.setPagoFondosReservaMes(daoRequest.getPagoFondosReservaMes());
            entity.setImpuestoRentaPatron(daoRequest.getImpuestoRentaPatron());
            entity.setVacaciones(daoRequest.getVacaciones());
            entity.setBeneficios(daoRequest.getBeneficios());
            entity.setPagoDecTercerCuartoMes(daoRequest.getPagoDecTercerCuartoMes());
            entity.setCuentaContableNombre(daoRequest.getCuentaContableNombre());
            entity.setAcumuladoAportePersonalIess(daoRequest.getAcumuladoAportePersonalIess());
            entity.setAcumuladoDecimoTercero(daoRequest.getAcumuladoDecimoTercero());
            entity.setAcumuladoFondosReserva(daoRequest.getAcumuladoFondosReserva());
            entity.setAcumuladoVacaciones(daoRequest.getAcumuladoVacaciones());
            entity.setAcumuladoImpuestoRenta(daoRequest.getAcumuladoImpuestoRenta());
            entity.setUtilidadCargas(daoRequest.getUtilidadCargas());
            entity.setTiempoParcial(daoRequest.getTiempoParcial());
            entity.setFactorParcial(daoRequest.getFactorParcial());
            entity.setPasante(daoRequest.getPasante());
            entity.setReingreso(daoRequest.getReingreso());
            entity.setFechaReingreso(daoRequest.getFechaReingreso());
            entity.setFechaIngreso(daoRequest.getFechaIngreso());
            entity.setProvDecimoTercero(daoRequest.getProvDecimoTercero());
            entity.setDecimoCuarto(daoRequest.getDecimoCuarto());
            entity.setProvDecimoCuarto(daoRequest.getProvDecimoCuarto());
            entity.setProvFondosReserva(daoRequest.getProvFondosReserva());
            entity.setProvVacaciones(daoRequest.getProvVacaciones());
            entity.setProvAportePatronal(daoRequest.getProvAportePatronal());
            Agencia agencia = new Agencia();
            agencia.setId(daoRequest.getIdAgencia());
            entity.setIdAgencia(agencia);
            Usuario usuario = new Usuario();
            usuario.setId(daoRequest.getIdUsuario());
            entity.setIdUsuario(usuario);
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            TrabajadorResponseDao dao = new TrabajadorResponseDao();

            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setEstadoCivil(entity.getEstadoCivil());
            dao.setGenero(entity.getGenero());
            dao.setGrupoSanguineo(entity.getGrupoSanguineo());
            dao.setNivelEstudios(entity.getNivelEstudios());
            dao.setProfesion(entity.getProfesion());
            dao.setDireccionDomiciliaria(entity.getDireccionDomiciliaria());
            dao.setEstadoFamiliar(entity.getEstadoFamiliar());
            dao.setNumeroAfiliacion(entity.getNumeroAfiliacion());
            dao.setDiscapacidad(entity.getDiscapacidad());
            dao.setTipoDiscapacidad(entity.getTipoDiscapacidad());
            dao.setSueldo(entity.getSueldo());
            dao.setBonificacion(entity.getBonificacion());
            dao.setMovilizacionEspecial(entity.getMovilizacionEspecial());
            dao.setComponenteSalarialUnifi(entity.getComponenteSalarialUnifi());
            dao.setRetencionesJudiciales(entity.getRetencionesJudiciales());
            dao.setPolizaPersonal(entity.getPolizaPersonal());
            dao.setAporteIess(entity.getAporteIess());
            dao.setDecimosAnio(entity.getDecimosAnio());
            dao.setImpuestoRenta(entity.getImpuestoRenta());
            dao.setFondoReservaIess(entity.getFondoReservaIess());
            dao.setUtilidades(entity.getUtilidades());
            dao.setPagoFondosReservaMes(entity.getPagoFondosReservaMes());
            dao.setImpuestoRentaPatron(entity.getImpuestoRentaPatron());
            dao.setVacaciones(entity.getVacaciones());
            dao.setBeneficios(entity.getBeneficios());
            dao.setPagoDecTercerCuartoMes(entity.getPagoDecTercerCuartoMes());
            dao.setCuentaContableNombre(entity.getCuentaContableNombre());
            dao.setAcumuladoAportePersonalIess(entity.getAcumuladoAportePersonalIess());
            dao.setAcumuladoDecimoTercero(entity.getAcumuladoDecimoTercero());
            dao.setAcumuladoFondosReserva(entity.getAcumuladoFondosReserva());
            dao.setAcumuladoVacaciones(entity.getAcumuladoVacaciones());
            dao.setAcumuladoImpuestoRenta(entity.getAcumuladoImpuestoRenta());
            dao.setUtilidadCargas(entity.getUtilidadCargas());
            dao.setTiempoParcial(entity.getTiempoParcial());
            dao.setFactorParcial(entity.getFactorParcial());
            dao.setPasante(entity.getPasante());
            dao.setReingreso(entity.getReingreso());
            dao.setFechaReingreso(entity.getFechaReingreso());
            dao.setFechaIngreso(entity.getFechaIngreso());
            dao.setProvDecimoTercero(entity.getProvDecimoTercero());
            dao.setDecimoCuarto(entity.getDecimoCuarto());
            dao.setProvDecimoCuarto(entity.getProvDecimoCuarto());
            dao.setProvFondosReserva(entity.getProvFondosReserva());
            dao.setProvVacaciones(entity.getProvVacaciones());
            dao.setProvAportePatronal(entity.getProvAportePatronal());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());

            if (entity.getIdUsuario() != null && entity.getIdUsuario().getId() != null) {
                dao.setIdUsuario(UsuarioMapper.setEntityToDaoReference.apply(entity.getIdUsuario()));
            }

            if (entity.getIdSupervisor() != null) {
                entity.getIdSupervisor().setOnlyChildrenData(true);
                dao.setIdSupervisor(SupervisorMapper.setEntityToDaoResponse.apply(entity.getIdSupervisor()));
            }

            if (entity.getIdVendedor() != null) {
                entity.getIdVendedor().setOnlyChildrenData(true);
                dao.setIdVendedor(VendedorMapper.setEntityToDaoResponse.apply(entity.getIdVendedor()));
            }

            if (entity.getCargaFamiliarCollection() != null) {
                entity.getCargaFamiliarCollection().forEach(cargaFamiliar -> {
                    cargaFamiliar.setOnlyChildrenData(true);
                    dao.getCargaFamiliarCollection()
                            .add(CargaFamiliarMapper.setEntityToDaoResponse.apply(cargaFamiliar));
                });
                dao.setCargaFamiliarCollection(
                        CargaFamiliarMapper.setEntityListToDaoResponseList.apply(entity.getCargaFamiliarCollection()));
            }

            if (entity.getHistorialLaboralCollection() != null) {
                entity.getHistorialLaboralCollection().forEach(historialLaboral -> {
                    historialLaboral.setOnlyChildrenData(true);
                });
                dao.setHistorialLaboralCollection(
                        HistorialLaboralMapper.setEntityListToDaoResponseList
                                .apply(entity.getHistorialLaboralCollection()));
            }

            if (entity.getMemorandumCollection() != null) {
                entity.getMemorandumCollection().forEach(memorandum -> {
                    memorandum.setOnlyChildrenData(true);
                });
                dao.setMemorandumCollection(
                        MemorandumMapper.setEntityListToDaoResponseList.apply(entity.getMemorandumCollection()));
            }

            if (entity.getInformacionFinancieraCollection() != null) {
                entity.getInformacionFinancieraCollection().forEach(informacionFinanciera -> {
                    informacionFinanciera.setOnlyChildrenData(true);
                });
                dao.setInformacionFinancieraCollection(
                        InformacionFinancieraMapper.setEntityListToDaoResponseList
                                .apply(entity.getInformacionFinancieraCollection()));
            }

            if (entity.getPrestamoCollection() != null) {
                entity.getPrestamoCollection().forEach(prestamo -> {
                    prestamo.setOnlyChildrenData(true);
                });
                dao.setPrestamoCollection(
                        PrestamoMapper.setEntityListToDaoResponseList.apply(entity.getPrestamoCollection()));
            }

            if (entity.getCargoVacacionCollection() != null) {
                entity.getCargoVacacionCollection().forEach(cargoVacacion -> {
                    cargoVacacion.setOnlyChildrenData(true);
                });
                dao.setCargoVacacionCollection(
                        CargoVacacionMapper.setEntityListToDaoResponseList.apply(entity.getCargoVacacionCollection()));
            }

            if (entity.getSriGastosCollection() != null) {
                entity.getSriGastosCollection().forEach(sriGastos -> {
                    sriGastos.setOnlyChildrenData(true);
                });
                dao.setSriGastosCollection(
                        SriGastosMapper.setEntityListToDaoResponseList.apply(entity.getSriGastosCollection()));
            }

            if (entity.getDetalleNovedadRolPagoCollection() != null) {
                entity.getDetalleNovedadRolPagoCollection().forEach(detalleNovedadRolPago -> {
                    detalleNovedadRolPago.setOnlyChildrenData(true);
                });

                dao.setDetalleNovedadRolPagoCollection(
                        DetalleNovedadRolPagoMapper.setEntityListToDaoResponseList
                                .apply(entity.getDetalleNovedadRolPagoCollection()));
            }

            if(entity.getPagosDosCollection() != null){
                entity.getPagosDosCollection().forEach(pagosDos -> {
                    pagosDos.setOnlyChildrenData(true);
                });
                dao.setPagosDosCollection(
                        PagosDosMapper.setEntityListToDaoResponseList.apply(entity.getPagosDosCollection()));
            }



            return dao;

        });

        setEntityToDaoReference = (entity -> {
            TrabajadorResponseDao dao = new TrabajadorResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setEstadoCivil(entity.getEstadoCivil());
            dao.setGenero(entity.getGenero());
            dao.setGrupoSanguineo(entity.getGrupoSanguineo());
            dao.setNivelEstudios(entity.getNivelEstudios());
            dao.setProfesion(entity.getProfesion());
            dao.setDireccionDomiciliaria(entity.getDireccionDomiciliaria());
            dao.setEstadoFamiliar(entity.getEstadoFamiliar());
            dao.setNumeroAfiliacion(entity.getNumeroAfiliacion());
            dao.setDiscapacidad(entity.getDiscapacidad());
            dao.setTipoDiscapacidad(entity.getTipoDiscapacidad());
            dao.setSueldo(entity.getSueldo());
            dao.setBonificacion(entity.getBonificacion());
            dao.setMovilizacionEspecial(entity.getMovilizacionEspecial());
            dao.setComponenteSalarialUnifi(entity.getComponenteSalarialUnifi());
            dao.setRetencionesJudiciales(entity.getRetencionesJudiciales());
            dao.setPolizaPersonal(entity.getPolizaPersonal());
            dao.setAporteIess(entity.getAporteIess());
            dao.setDecimosAnio(entity.getDecimosAnio());
            dao.setImpuestoRenta(entity.getImpuestoRenta());
            dao.setFondoReservaIess(entity.getFondoReservaIess());
            dao.setUtilidades(entity.getUtilidades());
            dao.setPagoFondosReservaMes(entity.getPagoFondosReservaMes());
            dao.setImpuestoRentaPatron(entity.getImpuestoRentaPatron());
            dao.setVacaciones(entity.getVacaciones());
            dao.setBeneficios(entity.getBeneficios());
            dao.setPagoDecTercerCuartoMes(entity.getPagoDecTercerCuartoMes());
            dao.setCuentaContableNombre(entity.getCuentaContableNombre());
            dao.setAcumuladoAportePersonalIess(entity.getAcumuladoAportePersonalIess());
            dao.setAcumuladoDecimoTercero(entity.getAcumuladoDecimoTercero());
            dao.setAcumuladoFondosReserva(entity.getAcumuladoFondosReserva());
            dao.setAcumuladoVacaciones(entity.getAcumuladoVacaciones());
            dao.setAcumuladoImpuestoRenta(entity.getAcumuladoImpuestoRenta());
            dao.setUtilidadCargas(entity.getUtilidadCargas());
            dao.setTiempoParcial(entity.getTiempoParcial());
            dao.setFactorParcial(entity.getFactorParcial());
            dao.setPasante(entity.getPasante());
            dao.setReingreso(entity.getReingreso());
            dao.setFechaReingreso(entity.getFechaReingreso());
            dao.setFechaIngreso(entity.getFechaIngreso());
            dao.setProvDecimoTercero(entity.getProvDecimoTercero());
            dao.setDecimoCuarto(entity.getDecimoCuarto());
            dao.setProvDecimoCuarto(entity.getProvDecimoCuarto());
            dao.setProvFondosReserva(entity.getProvFondosReserva());
            dao.setProvVacaciones(entity.getProvVacaciones());
            dao.setProvAportePatronal(entity.getProvAportePatronal());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());

            if (entity.getIdUsuario() != null && entity.getIdUsuario().getId() != null) {
                dao.setIdUsuario(UsuarioMapper.setEntityToDaoReference.apply(entity.getIdUsuario()));
            }
            return dao;
        });

        setEntityToDaoReferenceUsario = (entity -> {
            TrabajadorResponseDao dao = new TrabajadorResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setEstadoCivil(entity.getEstadoCivil());
            dao.setGenero(entity.getGenero());
            dao.setGrupoSanguineo(entity.getGrupoSanguineo());
            dao.setNivelEstudios(entity.getNivelEstudios());
            dao.setProfesion(entity.getProfesion());
            dao.setDireccionDomiciliaria(entity.getDireccionDomiciliaria());
            dao.setEstadoFamiliar(entity.getEstadoFamiliar());
            dao.setNumeroAfiliacion(entity.getNumeroAfiliacion());
            dao.setDiscapacidad(entity.getDiscapacidad());
            dao.setTipoDiscapacidad(entity.getTipoDiscapacidad());
            dao.setSueldo(entity.getSueldo());
            dao.setBonificacion(entity.getBonificacion());
            dao.setMovilizacionEspecial(entity.getMovilizacionEspecial());
            dao.setComponenteSalarialUnifi(entity.getComponenteSalarialUnifi());
            dao.setRetencionesJudiciales(entity.getRetencionesJudiciales());
            dao.setPolizaPersonal(entity.getPolizaPersonal());
            dao.setAporteIess(entity.getAporteIess());
            dao.setDecimosAnio(entity.getDecimosAnio());
            dao.setImpuestoRenta(entity.getImpuestoRenta());
            dao.setFondoReservaIess(entity.getFondoReservaIess());
            dao.setUtilidades(entity.getUtilidades());
            dao.setPagoFondosReservaMes(entity.getPagoFondosReservaMes());
            dao.setImpuestoRentaPatron(entity.getImpuestoRentaPatron());
            dao.setVacaciones(entity.getVacaciones());
            dao.setBeneficios(entity.getBeneficios());
            dao.setPagoDecTercerCuartoMes(entity.getPagoDecTercerCuartoMes());
            dao.setCuentaContableNombre(entity.getCuentaContableNombre());
            dao.setAcumuladoAportePersonalIess(entity.getAcumuladoAportePersonalIess());
            dao.setAcumuladoDecimoTercero(entity.getAcumuladoDecimoTercero());
            dao.setAcumuladoFondosReserva(entity.getAcumuladoFondosReserva());
            dao.setAcumuladoVacaciones(entity.getAcumuladoVacaciones());
            dao.setAcumuladoImpuestoRenta(entity.getAcumuladoImpuestoRenta());
            dao.setUtilidadCargas(entity.getUtilidadCargas());
            dao.setTiempoParcial(entity.getTiempoParcial());
            dao.setFactorParcial(entity.getFactorParcial());
            dao.setPasante(entity.getPasante());
            dao.setReingreso(entity.getReingreso());
            dao.setFechaReingreso(entity.getFechaReingreso());
            dao.setFechaIngreso(entity.getFechaIngreso());
            dao.setProvDecimoTercero(entity.getProvDecimoTercero());
            dao.setDecimoCuarto(entity.getDecimoCuarto());
            dao.setProvDecimoCuarto(entity.getProvDecimoCuarto());
            dao.setProvFondosReserva(entity.getProvFondosReserva());
            dao.setProvVacaciones(entity.getProvVacaciones());
            dao.setProvAportePatronal(entity.getProvAportePatronal());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            if (entity.getIdUsuario() != null) {
                dao.setIdUsuario(UsuarioMapper.setEntityToDaoReference.apply(entity.getIdUsuario()));
            }
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<TrabajadorResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(TrabajadorMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> entityList.stream().map(TrabajadorMapper.setEntityToDaoReference).collect(Collectors.toList()));
    }
}
