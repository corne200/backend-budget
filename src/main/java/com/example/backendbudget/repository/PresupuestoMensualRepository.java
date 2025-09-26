package com.example.backendbudget.repository;

import com.example.backendbudget.model.PresupuestoMensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PresupuestoMensualRepository extends JpaRepository<PresupuestoMensual, Long> {
    @Query(value = "SELECT * FROM \"PresupuestoMensual\"", nativeQuery = true)
    List<PresupuestoMensual> findAllNative();

    Optional<PresupuestoMensual> findFirstByActivoTrueOrderByAnioDescMesDesc();
}
