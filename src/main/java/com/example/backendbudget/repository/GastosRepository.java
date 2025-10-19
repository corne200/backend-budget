package com.example.backendbudget.repository;

import com.example.backendbudget.model.Gastos;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GastosRepository extends JpaRepository<Gastos, Long> {

    @Query("SELECT g FROM Gastos g JOIN FETCH g.categoria WHERE g.presupuesto.presupuestoId = :presupuestoId")
    List<Gastos> findByPresupuesto_PresupuestoId(@Param("presupuestoId") Long presupuestoId);

}
