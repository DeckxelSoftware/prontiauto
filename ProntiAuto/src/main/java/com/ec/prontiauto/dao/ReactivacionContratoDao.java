package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractContratatoDao;

public class ReactivacionContratoDao extends AbstractContratatoDao {
    private Integer idVendedor;
    private boolean porcentajeTasa;

    public ReactivacionContratoDao() {
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public boolean isPorcentajeTasa() {
        return porcentajeTasa;
    }

    public void setPorcentajeTasa(boolean porcentajeTasa) {
        this.porcentajeTasa = porcentajeTasa;
    }

}
