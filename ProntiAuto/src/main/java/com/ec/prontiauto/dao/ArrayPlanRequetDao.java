package com.ec.prontiauto.dao;

public class ArrayPlanRequetDao {
    private PlanRequestDao[] planes;

    public ArrayPlanRequetDao() {
    }

    public PlanRequestDao[] getPlanes() {
        return planes;
    }

    public void setPlanes(PlanRequestDao[] planes) {
        this.planes = planes;
    }

}
