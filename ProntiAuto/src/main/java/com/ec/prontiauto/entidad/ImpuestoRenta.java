package com.ec.prontiauto.entidad;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "impuesto_renta")
public class ImpuestoRenta extends AbstractEntities {

    @Column(name = "nombre", nullable = false, length = 50)
    @CsvBindByName(column = "nombre")
    private String nombre;

    @Column(name = "anio", nullable = false)
    @CsvBindByName(column = "anio")
    private Integer anio;

    @Column(name = "\"fraccionBasica1\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_1")
    private BigDecimal fraccionBasica1;

    @Column(name = "\"impuestoFraccionBasica1\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_1")
    private BigDecimal impuestoFraccionBasica1;

    @Column(name = "\"impuestoFraccionExcedente1\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_1")
    private BigDecimal impuestoFraccionExcedente1;

    @Column(name = "\"fraccionBasica2\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_2")
    private BigDecimal fraccionBasica2;

    @Column(name = "\"impuestoFraccionBasica2\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_2")
    private BigDecimal impuestoFraccionBasica2;

    @Column(name = "\"impuestoFraccionExcedente2\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_2")
    private BigDecimal impuestoFraccionExcedente2;

    @Column(name = "\"fraccionBasica3\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_3")
    private BigDecimal fraccionBasica3;

    @Column(name = "\"impuestoFraccionBasica3\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_3")
    private BigDecimal impuestoFraccionBasica3;

    @Column(name = "\"impuestoFraccionExcedente3\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_3")
    private BigDecimal impuestoFraccionExcedente3;

    @Column(name = "\"fraccionBasica4\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_4")
    private BigDecimal fraccionBasica4;

    @Column(name = "\"impuestoFraccionBasica4\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_4")
    private BigDecimal impuestoFraccionBasica4;

    @Column(name = "\"impuestoFraccionExcedente4\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_4")
    private BigDecimal impuestoFraccionExcedente4;

    @Column(name = "\"fraccionBasica5\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_5")
    private BigDecimal fraccionBasica5;

    @Column(name = "\"impuestoFraccionBasica5\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_5")
    private BigDecimal impuestoFraccionBasica5;

    @Column(name = "\"impuestoFraccionExcedente5\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_5")
    private BigDecimal impuestoFraccionExcedente5;

    @Column(name = "\"fraccionBasica6\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_6")
    private BigDecimal fraccionBasica6;

    @Column(name = "\"impuestoFraccionBasica6\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_6")
    private BigDecimal impuestoFraccionBasica6;

    @Column(name = "\"impuestoFraccionExcedente6\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_6")
    private BigDecimal impuestoFraccionExcedente6;

    @Column(name = "\"fraccionBasica7\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_7")
    private BigDecimal fraccionBasica7;

    @Column(name = "\"impuestoFraccionBasica7\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_7")
    private BigDecimal impuestoFraccionBasica7;

    @Column(name = "\"impuestoFraccionExcedente7\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_7")
    private BigDecimal impuestoFraccionExcedente7;

    @Column(name = "\"fraccionBasica8\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_8")
    private BigDecimal fraccionBasica8;

    @Column(name = "\"impuestoFraccionBasica8\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_8")
    private BigDecimal impuestoFraccionBasica8;

    @Column(name = "\"impuestoFraccionExcedente8\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_8")
    private BigDecimal impuestoFraccionExcedente8;

    @Column(name = "\"fraccionBasica9\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_9")
    private BigDecimal fraccionBasica9;

    @Column(name = "\"impuestoFraccionBasica9\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_9")
    private BigDecimal impuestoFraccionBasica9;

    @Column(name = "\"impuestoFraccionExcedente9\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_9")
    private BigDecimal impuestoFraccionExcedente9;

    @Column(name = "\"fraccionBasica10\"", nullable = false)
    @CsvBindByName(column = "fraccion_basica_10")
    private BigDecimal fraccionBasica10;

    @Column(name = "\"impuestoFraccionBasica10\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_basica_10")
    private BigDecimal impuestoFraccionBasica10;

    @Column(name = "\"impuestoFraccionExcedente10\"", nullable = false)
    @CsvBindByName(column = "impuesto_fraccion_excedente_10")
    private BigDecimal impuestoFraccionExcedente10;

    public ImpuestoRenta() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getFraccionBasica1() {
        return fraccionBasica1;
    }

    public void setFraccionBasica1(BigDecimal fraccionBasica1) {
        this.fraccionBasica1 = fraccionBasica1;
    }

    public BigDecimal getImpuestoFraccionBasica1() {
        return impuestoFraccionBasica1;
    }

    public void setImpuestoFraccionBasica1(BigDecimal impuestoFraccionBasica1) {
        this.impuestoFraccionBasica1 = impuestoFraccionBasica1;
    }

    public BigDecimal getImpuestoFraccionExcedente1() {
        return impuestoFraccionExcedente1;
    }

    public void setImpuestoFraccionExcedente1(BigDecimal impuestoFraccionExcedente1) {
        this.impuestoFraccionExcedente1 = impuestoFraccionExcedente1;
    }

    public BigDecimal getFraccionBasica2() {
        return fraccionBasica2;
    }

    public void setFraccionBasica2(BigDecimal fraccionBasica2) {
        this.fraccionBasica2 = fraccionBasica2;
    }

    public BigDecimal getImpuestoFraccionBasica2() {
        return impuestoFraccionBasica2;
    }

    public void setImpuestoFraccionBasica2(BigDecimal impuestoFraccionBasica2) {
        this.impuestoFraccionBasica2 = impuestoFraccionBasica2;
    }

    public BigDecimal getImpuestoFraccionExcedente2() {
        return impuestoFraccionExcedente2;
    }

    public void setImpuestoFraccionExcedente2(BigDecimal impuestoFraccionExcedente2) {
        this.impuestoFraccionExcedente2 = impuestoFraccionExcedente2;
    }

    public BigDecimal getFraccionBasica3() {
        return fraccionBasica3;
    }

    public void setFraccionBasica3(BigDecimal fraccionBasica3) {
        this.fraccionBasica3 = fraccionBasica3;
    }

    public BigDecimal getImpuestoFraccionBasica3() {
        return impuestoFraccionBasica3;
    }

    public void setImpuestoFraccionBasica3(BigDecimal impuestoFraccionBasica3) {
        this.impuestoFraccionBasica3 = impuestoFraccionBasica3;
    }

    public BigDecimal getImpuestoFraccionExcedente3() {
        return impuestoFraccionExcedente3;
    }

    public void setImpuestoFraccionExcedente3(BigDecimal impuestoFraccionExcedente3) {
        this.impuestoFraccionExcedente3 = impuestoFraccionExcedente3;
    }

    public BigDecimal getFraccionBasica4() {
        return fraccionBasica4;
    }

    public void setFraccionBasica4(BigDecimal fraccionBasica4) {
        this.fraccionBasica4 = fraccionBasica4;
    }

    public BigDecimal getImpuestoFraccionBasica4() {
        return impuestoFraccionBasica4;
    }

    public void setImpuestoFraccionBasica4(BigDecimal impuestoFraccionBasica4) {
        this.impuestoFraccionBasica4 = impuestoFraccionBasica4;
    }

    public BigDecimal getImpuestoFraccionExcedente4() {
        return impuestoFraccionExcedente4;
    }

    public void setImpuestoFraccionExcedente4(BigDecimal impuestoFraccionExcedente4) {
        this.impuestoFraccionExcedente4 = impuestoFraccionExcedente4;
    }

    public BigDecimal getFraccionBasica5() {
        return fraccionBasica5;
    }

    public void setFraccionBasica5(BigDecimal fraccionBasica5) {
        this.fraccionBasica5 = fraccionBasica5;
    }

    public BigDecimal getImpuestoFraccionBasica5() {
        return impuestoFraccionBasica5;
    }

    public void setImpuestoFraccionBasica5(BigDecimal impuestoFraccionBasica5) {
        this.impuestoFraccionBasica5 = impuestoFraccionBasica5;
    }

    public BigDecimal getImpuestoFraccionExcedente5() {
        return impuestoFraccionExcedente5;
    }

    public void setImpuestoFraccionExcedente5(BigDecimal impuestoFraccionExcedente5) {
        this.impuestoFraccionExcedente5 = impuestoFraccionExcedente5;
    }

    public BigDecimal getFraccionBasica6() {
        return fraccionBasica6;
    }

    public void setFraccionBasica6(BigDecimal fraccionBasica6) {
        this.fraccionBasica6 = fraccionBasica6;
    }

    public BigDecimal getImpuestoFraccionBasica6() {
        return impuestoFraccionBasica6;
    }

    public void setImpuestoFraccionBasica6(BigDecimal impuestoFraccionBasica6) {
        this.impuestoFraccionBasica6 = impuestoFraccionBasica6;
    }

    public BigDecimal getImpuestoFraccionExcedente6() {
        return impuestoFraccionExcedente6;
    }

    public void setImpuestoFraccionExcedente6(BigDecimal impuestoFraccionExcedente6) {
        this.impuestoFraccionExcedente6 = impuestoFraccionExcedente6;
    }

    public BigDecimal getFraccionBasica7() {
        return fraccionBasica7;
    }

    public void setFraccionBasica7(BigDecimal fraccionBasica7) {
        this.fraccionBasica7 = fraccionBasica7;
    }

    public BigDecimal getImpuestoFraccionBasica7() {
        return impuestoFraccionBasica7;
    }

    public void setImpuestoFraccionBasica7(BigDecimal impuestoFraccionBasica7) {
        this.impuestoFraccionBasica7 = impuestoFraccionBasica7;
    }

    public BigDecimal getImpuestoFraccionExcedente7() {
        return impuestoFraccionExcedente7;
    }

    public void setImpuestoFraccionExcedente7(BigDecimal impuestoFraccionExcedente7) {
        this.impuestoFraccionExcedente7 = impuestoFraccionExcedente7;
    }

    public BigDecimal getFraccionBasica8() {
        return fraccionBasica8;
    }

    public void setFraccionBasica8(BigDecimal fraccionBasica8) {
        this.fraccionBasica8 = fraccionBasica8;
    }

    public BigDecimal getImpuestoFraccionBasica8() {
        return impuestoFraccionBasica8;
    }

    public void setImpuestoFraccionBasica8(BigDecimal impuestoFraccionBasica8) {
        this.impuestoFraccionBasica8 = impuestoFraccionBasica8;
    }

    public BigDecimal getImpuestoFraccionExcedente8() {
        return impuestoFraccionExcedente8;
    }

    public void setImpuestoFraccionExcedente8(BigDecimal impuestoFraccionExcedente8) {
        this.impuestoFraccionExcedente8 = impuestoFraccionExcedente8;
    }

    public BigDecimal getFraccionBasica9() {
        return fraccionBasica9;
    }

    public void setFraccionBasica9(BigDecimal fraccionBasica9) {
        this.fraccionBasica9 = fraccionBasica9;
    }

    public BigDecimal getImpuestoFraccionBasica9() {
        return impuestoFraccionBasica9;
    }

    public void setImpuestoFraccionBasica9(BigDecimal impuestoFraccionBasica9) {
        this.impuestoFraccionBasica9 = impuestoFraccionBasica9;
    }

    public BigDecimal getImpuestoFraccionExcedente9() {
        return impuestoFraccionExcedente9;
    }

    public void setImpuestoFraccionExcedente9(BigDecimal impuestoFraccionExcedente9) {
        this.impuestoFraccionExcedente9 = impuestoFraccionExcedente9;
    }

    public BigDecimal getFraccionBasica10() {
        return fraccionBasica10;
    }

    public void setFraccionBasica10(BigDecimal fraccionBasica10) {
        this.fraccionBasica10 = fraccionBasica10;
    }

    public BigDecimal getImpuestoFraccionBasica10() {
        return impuestoFraccionBasica10;
    }

    public void setImpuestoFraccionBasica10(BigDecimal impuestoFraccionBasica10) {
        this.impuestoFraccionBasica10 = impuestoFraccionBasica10;
    }

    public BigDecimal getImpuestoFraccionExcedente10() {
        return impuestoFraccionExcedente10;
    }

    public void setImpuestoFraccionExcedente10(BigDecimal impuestoFraccionExcedente10) {
        this.impuestoFraccionExcedente10 = impuestoFraccionExcedente10;
    }

    public ImpuestoRenta setValoresDiferentes(ImpuestoRenta antiguo, ImpuestoRenta actual) {

        if (actual.getNombre() != null) {
            antiguo.setNombre(actual.getNombre());
        }
        if (actual.getAnio() != null) {
            antiguo.setAnio(actual.getAnio());
        }
        if (actual.getFraccionBasica1() != null) {
            antiguo.setFraccionBasica1(actual.getFraccionBasica1());
        }
        if (actual.getImpuestoFraccionBasica1() != null) {
            antiguo.setImpuestoFraccionBasica1(actual.getImpuestoFraccionBasica1());
        }
        if (actual.getImpuestoFraccionExcedente1() != null) {
            antiguo.setImpuestoFraccionExcedente1(actual.getImpuestoFraccionExcedente1());
        }
        if (actual.getFraccionBasica2() != null) {
            antiguo.setFraccionBasica2(actual.getFraccionBasica2());
        }
        if (actual.getImpuestoFraccionBasica2() != null) {
            antiguo.setImpuestoFraccionBasica2(actual.getImpuestoFraccionBasica2());
        }
        if (actual.getImpuestoFraccionExcedente2() != null) {
            antiguo.setImpuestoFraccionExcedente2(actual.getImpuestoFraccionExcedente2());
        }
        if (actual.getFraccionBasica3() != null) {
            antiguo.setFraccionBasica3(actual.getFraccionBasica3());
        }
        if (actual.getImpuestoFraccionBasica3() != null) {
            antiguo.setImpuestoFraccionBasica3(actual.getImpuestoFraccionBasica3());
        }
        if (actual.getImpuestoFraccionExcedente3() != null) {
            antiguo.setImpuestoFraccionExcedente3(actual.getImpuestoFraccionExcedente3());
        }
        if (actual.getFraccionBasica4() != null) {
            antiguo.setFraccionBasica4(actual.getFraccionBasica4());
        }
        if (actual.getImpuestoFraccionBasica4() != null) {
            antiguo.setImpuestoFraccionBasica4(actual.getImpuestoFraccionBasica4());
        }
        if (actual.getImpuestoFraccionExcedente4() != null) {
            antiguo.setImpuestoFraccionExcedente4(actual.getImpuestoFraccionExcedente4());
        }
        if (actual.getFraccionBasica5() != null) {
            antiguo.setFraccionBasica5(actual.getFraccionBasica5());
        }
        if (actual.getImpuestoFraccionBasica5() != null) {
            antiguo.setImpuestoFraccionBasica5(actual.getImpuestoFraccionBasica5());
        }
        if (actual.getImpuestoFraccionExcedente5() != null) {
            antiguo.setImpuestoFraccionExcedente5(actual.getImpuestoFraccionExcedente5());
        }
        if (actual.getFraccionBasica6() != null) {
            antiguo.setFraccionBasica6(actual.getFraccionBasica6());
        }
        if (actual.getImpuestoFraccionBasica6() != null) {
            antiguo.setImpuestoFraccionBasica6(actual.getImpuestoFraccionBasica6());
        }
        if (actual.getImpuestoFraccionExcedente6() != null) {
            antiguo.setImpuestoFraccionExcedente6(actual.getImpuestoFraccionExcedente6());
        }
        if (actual.getFraccionBasica7() != null) {
            antiguo.setFraccionBasica7(actual.getFraccionBasica7());
        }
        if (actual.getImpuestoFraccionBasica7() != null) {
            antiguo.setImpuestoFraccionBasica7(actual.getImpuestoFraccionBasica7());
        }
        if (actual.getImpuestoFraccionExcedente7() != null) {
            antiguo.setImpuestoFraccionExcedente7(actual.getImpuestoFraccionExcedente7());
        }
        if (actual.getFraccionBasica8() != null) {
            antiguo.setFraccionBasica8(actual.getFraccionBasica8());
        }
        if (actual.getImpuestoFraccionBasica8() != null) {
            antiguo.setImpuestoFraccionBasica8(actual.getImpuestoFraccionBasica8());
        }
        if (actual.getImpuestoFraccionExcedente8() != null) {
            antiguo.setImpuestoFraccionExcedente8(actual.getImpuestoFraccionExcedente8());
        }
        if (actual.getFraccionBasica9() != null) {
            antiguo.setFraccionBasica9(actual.getFraccionBasica9());
        }
        if (actual.getImpuestoFraccionBasica9() != null) {
            antiguo.setImpuestoFraccionBasica9(actual.getImpuestoFraccionBasica9());
        }
        if (actual.getImpuestoFraccionExcedente9() != null) {
            antiguo.setImpuestoFraccionExcedente9(actual.getImpuestoFraccionExcedente9());
        }
        if (actual.getFraccionBasica10() != null) {
            antiguo.setFraccionBasica10(actual.getFraccionBasica10());
        }
        if (actual.getImpuestoFraccionBasica10() != null) {
            antiguo.setImpuestoFraccionBasica10(actual.getImpuestoFraccionBasica10());
        }
        if (actual.getImpuestoFraccionExcedente10() != null) {
            antiguo.setImpuestoFraccionExcedente10(actual.getImpuestoFraccionExcedente10());
        }

        return antiguo;
    }
}
