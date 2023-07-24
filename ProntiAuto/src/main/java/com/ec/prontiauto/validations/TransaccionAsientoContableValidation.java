package com.ec.prontiauto.validations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.time.LocalDate;
import java.util.List;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.ec.prontiauto.dao.TransaccionAsientoContableRequestDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.entidad.PeriodoContable;
import com.ec.prontiauto.entidad.TransaccionAsientoContable;
import com.ec.prontiauto.exception.ApiRequestException;

public class TransaccionAsientoContableValidation {
    private EntityManager em;
    private TransaccionAsientoContableRequestDao dao;
    private Boolean isUpdate;

    private AsientoContableCabecera asientoContableCabecera;

    public TransaccionAsientoContableValidation(EntityManager em, TransaccionAsientoContableRequestDao dao, Boolean isUpdate) {
        this.em = em;
        this.dao = dao;
        this.isUpdate = isUpdate;

        this.asientoContableCabecera = null;

        this.initState();
    }


    private void initState() {
        actualizarDebitoCredito();
        gestionSaldo();
    }

    private AsientoContableCabecera  getAsientoContablePeriodo(Integer idAsientoContable) {
        AsientoContableCabecera asientoContableCabecera;
        try {

            String QueryText = "SELECT p FROM AsientoContableCabecera p "
                    + "INNER JOIN CuentaContable cc "
                    + "ON p.idCuentaContable = cc.id "
                    + "INNER JOIN PeriodoContable pc "
                    + "ON cc.idPeriodoContable = pc.id "
                    + "WHERE p.id=:id";

            Query query = this.em.createQuery(QueryText)
                    .setParameter("id", idAsientoContable);
            asientoContableCabecera = (AsientoContableCabecera) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException("No se encontró el asiento contable cabecera");
        }

        return asientoContableCabecera;
    }

    private AsientoContableCabecera getAsientoContable(Integer idAsientoContable) {
        AsientoContableCabecera asientoContableCabecera = null;
        if( idAsientoContable == null) {
            throw  new ApiRequestException("Ingrese el id del asiento contable");
        }

        try {
            String QueryText = "SELECT p FROM AsientoContableCabecera p "
                    + "WHERE p.id=:id";

            Query query = this.em.createQuery(QueryText)
                    .setParameter("id", idAsientoContable);
            asientoContableCabecera = (AsientoContableCabecera) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException("No se encontró el asiento contable cabecera");
        }
        return asientoContableCabecera;
    }

    private TransaccionAsientoContable getTransaccion(Integer id){
        TransaccionAsientoContable transaccion = null;
        if( id == null) {
            throw  new ApiRequestException("Ingrese el id de la transacción");
        }

        try {
            String QueryText = "SELECT p FROM TransaccionAsientoContable p "
                    + "WHERE p.id=:id";

            Query query = this.em.createQuery(QueryText)
                    .setParameter("id", id);
            transaccion = (TransaccionAsientoContable) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException("No se encontró el asiento contable cabecera");
        }
        return transaccion;
    }

    private List<TransaccionAsientoContable> transaccionAsientoContable(Integer idAsientoContable) {
        List<TransaccionAsientoContable> transaccionAsientoContable = null;
        if( idAsientoContable == null) {
            throw  new ApiRequestException("Ingrese el id del asiento contable");
        }
        try {
            String QueryText = "select tc " +
                    "from TransaccionAsientoContable tc " +
                    "inner join AsientoContableCabecera ac " +
                    "on tc.idAsientoContableCabecera=ac.id " +
                    "where ac.id=:id";

            Query query = this.em.createQuery(QueryText)
                    .setParameter("id", idAsientoContable);
            transaccionAsientoContable = (List<TransaccionAsientoContable>) query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            transaccionAsientoContable = null;
        }
        return transaccionAsientoContable;
    }
    private void actualizarDebitoCredito() {

        Integer idAsientoContable = null;
        Float valorCreditoDao = dao.getValorCredito();
        Float valorDebitoDao = dao.getValorDebito();

        if(!isUpdate){

            if(valorDebitoDao != null || valorCreditoDao != null) {

                if(dao.getIdAsientoContableCabecera() != null) {
                    idAsientoContable = (int) dao.getIdAsientoContableCabecera();
                }else {
                    throw new ApiRequestException("Ingrese el id del asiento contable");
                }


                Float valorDebito = dao.getValorDebito();
                Float valorCredito = dao.getValorCredito();
                Float valorDebitoAux = 0.0f;
                Float valorCreditoAux = 0.0f;
                AsientoContableCabecera asientoContableCabecera = getAsientoContable(idAsientoContable);
                Float asientoContableDebito = asientoContableCabecera.getTotalDebito();
                Float asientoContableCredito = asientoContableCabecera.getTotalCredito();

                if(valorDebito != null ){

                    if (asientoContableDebito != null && valorDebito != null) {
                        valorDebitoAux = asientoContableDebito + valorDebito;
                    } else if (valorDebito != null) {
                        valorDebitoAux = valorDebito;
                    } else if (asientoContableDebito != null) {
                        valorDebitoAux = asientoContableDebito;
                    }

                    asientoContableCabecera.setTotalDebito(valorDebitoAux);
                }

                if(valorCredito != null){
                    if (asientoContableCredito != null && valorCredito != null) {
                        valorCreditoAux = valorCredito + asientoContableCredito;
                    } else if (valorCredito != null) {
                        valorCreditoAux = valorCredito;
                    } else if (asientoContableCredito != null) {
                        valorCreditoAux = asientoContableCredito;
                    }

                    asientoContableCabecera.setTotalCredito(valorCreditoAux);
                }

                this.em.merge(asientoContableCabecera);
                this.asientoContableCabecera = asientoContableCabecera;
            }

        }else {
           TransaccionAsientoContable transaccion = getTransaccion(dao.getId());
           List<TransaccionAsientoContable> transaccionCollection = transaccionAsientoContable(transaccion.getIdAsientoContableCabecera().getId());

            AsientoContableCabecera asientoContableCabecera = transaccionCollection.get(0).getIdAsientoContableCabecera();
            Float totalCredito = 0.0f;
            Float totalDebito = 0.0f;

            Float creditoDB = null;
            Float debitoDB = null;


            for (int i = 0; i < transaccionCollection.size(); i++) {

                if(transaccionCollection.get(i).getId() == transaccion.getId()){

                    if( dao.getSisHabilitado() != null && dao.getSisHabilitado().equals("D")){
                        transaccionCollection.get(i).setValorDebito(0.0f);
                        transaccionCollection.get(i).setValorCredito(0.0f);
                        dao.setSisHabilitado("I");

                    }else {
                        if(valorDebitoDao != null) {
                            transaccionCollection.get(i).setValorDebito(valorDebitoDao);
                        }

                        if(valorCreditoDao != null) {
                            transaccionCollection.get(i).setValorCredito(valorCreditoDao);
                        }

                    }
                }

                creditoDB = transaccionCollection.get(i).getValorCredito();
                if(creditoDB != null){
                    totalCredito += creditoDB.floatValue();
                }

                debitoDB = transaccionCollection.get(i).getValorDebito();
                if(debitoDB != null){
                    totalDebito += debitoDB.floatValue();
                }

            }

            asientoContableCabecera.setTotalCredito(totalCredito);
            asientoContableCabecera.setTotalDebito(totalDebito);

            this.em.merge(asientoContableCabecera);
            this.asientoContableCabecera = asientoContableCabecera;

        }
    }

    private void gestionSaldo() {
        /*
         * Requerimiento # 346/347
         * 
         * Cuando se crea una transacción contable.
         * Entonces en la cuenta contable de la transacción se almacena el valor del
         * débito o el crédito de la transacción respectivamente.
         * 
         * Entonces entonces se verifica si la cuenta es acreedora o deudora
         * Y si la cuenta es deudora
         * Entonces el saldo actual y el saldo del mes del periodo van a ser igual a:
         * saldo(mes)= actual_saldo +debito(mes) - credito(mes)
         * actual_saldo = actual_saldo +debito(mes) - credito(mes)
         * 
         * Si la cuenta es acreedora
         * Entonces el saldo actual y el saldo del mes del periodo van a ser igual a:
         * saldo(mes) = actual_saldo - débito(mes) + crédito(mes)
         * actual_saldo = actual_saldo -débito(mes) + crédito(mes)
         * 
         * Y el anterior débito o anterior crédito serán 0
         * Y el anterior saldo es el saldo actual del año anterior de la cuenta.
         * 
         */

        Float valorCreditoDao = dao.getValorCredito();
        Float valorDebitoDao = dao.getValorDebito();

        if(valorDebitoDao != null || valorCreditoDao != null) {

            Integer idAsientoContable = null;

            if(asientoContableCabecera != null) {
                idAsientoContable = asientoContableCabecera.getId() ;
            }else {
                idAsientoContable = (int) dao.getIdAsientoContableCabecera();
            }


            Float valorDebito = dao.getValorDebito();
            Float valorCredito = dao.getValorCredito();
            AsientoContableCabecera asientoContableCabecera = getAsientoContablePeriodo(idAsientoContable);
            CuentaContable cuentaContable = asientoContableCabecera.getIdCuentaContable();

            if (cuentaContable == null) {
                throw new ApiException("El AsientoContableCabecera especificado no posee una relación con cuentaContable");
            }

            PeriodoContable periodoContable = cuentaContable.getIdPeriodoContable();

            if (periodoContable == null) {
                throw new ApiException(
                        "La cuentaContable asociada al AsientoContable no posee una relación con periodoContable");
            }

            int currentMonth = LocalDate.now().getMonthValue();
            Boolean cuentaDeudora = cuentaContable.getTipoCuenta().equals("D");
            Boolean cuentaAcreedora = cuentaContable.getTipoCuenta().equals("A");

            Float debMes = 0.0f;
            Float credMes = 0.0f;
            Float saldoAux = 0.0f;
            Float saldoActual = cuentaContable.getActualSaldo();

            switch (currentMonth) {
                case 1:
                    if (valorDebito != null) {
                        cuentaContable.setEneroDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setEneroCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getEneroDebito();
                    credMes = cuentaContable.getEneroCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setEneroSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);

                    break;
                case 2:
                    if (valorDebito != null) {
                        cuentaContable.setFebreroDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setFebreroCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getFebreroDebito();
                    credMes = cuentaContable.getFebreroCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setFebreroSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);

                    break;
                case 3:

                    if (valorDebito != null) {
                        cuentaContable.setMarzoDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setMarzoCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getMarzoDebito();
                    credMes = cuentaContable.getMarzoCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoActual = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setMarzoSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 4:
                    if (valorDebito != null) {
                        cuentaContable.setAbrilDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setAbrilCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getAbrilDebito();
                    credMes = cuentaContable.getAbrilCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setAbrilSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);

                    break;
                case 5:
                    if (valorDebito != null) {
                        cuentaContable.setMayoDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setMayoCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getMayoDebito();
                    credMes = cuentaContable.getMayoCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setMayoSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 6:
                    if (valorDebito != null) {
                        cuentaContable.setJunioDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setJunioCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getJunioDebito();
                    credMes = cuentaContable.getJunioCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setJunioSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 7:
                    if (valorDebito != null) {
                        cuentaContable.setJulioDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setJulioCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getJulioDebito();
                    credMes = cuentaContable.getJulioCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setJulioSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 8:
                    if (valorDebito != null) {
                        cuentaContable.setAgostoDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setAgostoCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getAgostoDebito();
                    credMes = cuentaContable.getAgostoCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setAgostoSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 9:
                    if (valorDebito != null) {
                        cuentaContable.setSeptiembreDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setSeptiembreCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getSeptiembreDebito();
                    credMes = cuentaContable.getSeptiembreCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setSeptiembreSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 10:
                    if (valorDebito != null) {
                        cuentaContable.setOctubreDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setOctubreCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getOctubreDebito();
                    credMes = cuentaContable.getOctubreCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setOctubreSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 11:
                    if (valorDebito != null) {
                        cuentaContable.setNoviembreDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setNoviembreCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getNoviembreDebito();
                    credMes = cuentaContable.getNoviembreCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setNoviembreSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                case 12:
                    if (valorDebito != null) {
                        cuentaContable.setDiciembreDebito(valorDebito);
                        cuentaContable.setAnteriorDebito(0.0f);
                    }

                    if (valorCredito != null) {
                        cuentaContable.setDiciembreCredito(valorCredito);
                        cuentaContable.setAnteriorCredito(0.0f);
                    }

                    debMes = cuentaContable.getDiciembreDebito();
                    credMes = cuentaContable.getDiciembreCredito();

                    if (cuentaDeudora) {
                        saldoAux = saldoActual + debMes - credMes;
                    }

                    if (cuentaAcreedora) {
                        saldoAux = saldoActual - debMes + credMes;
                    }

                    cuentaContable.setDiciembreSaldo(saldoAux);
                    cuentaContable.setActualSaldo(saldoAux);
                    break;
                default:
                    throw new ApiException("El mes no es valido");
            }

            Float anteriorSaldo = cuentaContable.getActualSaldo();
            cuentaContable.setAnteriorSaldo(anteriorSaldo);

            this.em.merge(cuentaContable);
            this.asientoContableCabecera = asientoContableCabecera;


        }

    }

    public TransaccionAsientoContableRequestDao getEntity() {
        if(asientoContableCabecera != null){
            dao.setIdAsientoContableCabecera(asientoContableCabecera);
        }
        return dao;
    }


}
