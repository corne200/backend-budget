package com.example.backendbudget.repository;

import com.example.backendbudget.model.Gastos;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GastosRepository extends JpaRepository<Gastos, Long> {

    List<Gastos> findByPresupuesto_PresupuestoId(Long presupuestoId);

}
