package com.ec.prontiauto.dao;

import java.math.BigDecimal;

import com.ec.prontiauto.abstracts.AbstractContratatoDao;

public class ActualizarPlazoContratoDao extends AbstractContratatoDao {

    private BigDecimal dsctoRecargo;

    public ActualizarPlazoContratoDao() {
    }

    public BigDecimal getDsctoRecargo() {
        return dsctoRecargo;
    }

    public void setDsctoRecargo(BigDecimal dsctoRecargo) {
        this.dsctoRecargo = dsctoRecargo;
    }

}
