package com.example.backendbudget.model;

import java.math.BigDecimal;

public class PresupuestoMensualDTO {
    private int anio;
    private int mes;
    private BigDecimal sueldo;

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }
    public BigDecimal getSueldo() { return sueldo; }
    public void setSueldo(BigDecimal sueldo) { this.sueldo = sueldo; }
}
