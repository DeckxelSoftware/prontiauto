package com.ec.prontiauto.helper;

public class DatesHelper {

    public DatesHelper(){}

    public String getMonthByNumber(Integer numberMonth){
        switch (numberMonth){
            case 1:
                return "ENE";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "ABR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AGO";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DIC";
            default:
                return "MES INVÁLIDO";
        }
    }
}
