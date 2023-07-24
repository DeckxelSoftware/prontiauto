package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class RolPermisoResponseDao extends AbstractResponseDao {

    RolResponseDao idRol;
    PermisoResponseDao idPermiso;

    public RolPermisoResponseDao() {
    }

    public RolResponseDao getIdRol() {
        return idRol;
    }

    public void setIdRol(RolResponseDao idRol) {
        this.idRol = idRol;
    }

    public PermisoResponseDao getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(PermisoResponseDao idPermiso) {
        this.idPermiso = idPermiso;
    }

}
