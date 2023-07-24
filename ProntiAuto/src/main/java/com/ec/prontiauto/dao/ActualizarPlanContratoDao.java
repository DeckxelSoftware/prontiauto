package com.ec.prontiauto.dao;

import java.math.BigDecimal;

import com.ec.prontiauto.abstracts.AbstractContratatoDao;

public class ActualizarPlanContratoDao extends AbstractContratatoDao {
    private Integer idPlanAnterior;
    private BigDecimal dsctoRecargo;

    public ActualizarPlanContratoDao() {
    }

    public Integer getIdPlanAnterior() {
        return idPlanAnterior;
    }

    public void setIdPlanAnterior(Integer idPlanAnterior) {
        this.idPlanAnterior = idPlanAnterior;
    }

    public BigDecimal getDsctoRecargo() {
        return dsctoRecargo;
    }

    public void setDsctoRecargo(BigDecimal dsctoRecargo) {
        this.dsctoRecargo = dsctoRecargo;
    }

}
