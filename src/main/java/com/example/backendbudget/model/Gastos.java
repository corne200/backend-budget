package com.example.backendbudget.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Gastos")
public class Gastos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gastoId;

    @ManyToOne
    @JoinColumn(name = "presupuesto_id", nullable = false)
    private PresupuestoMensual presupuesto;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categorias categoria;

    private BigDecimal monto;
    private String descripcion;
    private LocalDate fecha;

    @Column(name = "categoria_id", insertable = false, updatable = false)
    private Long categoriaId;

    @Column(name = "presupuesto_id", insertable = false, updatable = false)
    private Long presupuestoId;


    // Getters y setters

    public Long getGastoId() {
        return gastoId;
    }

    public void setGastoId(Long gastoId) {
        this.gastoId = gastoId;
    }

    public PresupuestoMensual getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(PresupuestoMensual presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(Long presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

}

