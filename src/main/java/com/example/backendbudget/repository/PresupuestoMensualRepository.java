package com.example.backendbudget.repository;

import com.example.backendbudget.model.PresupuestoMensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface PresupuestoMensualRepository extends JpaRepository<PresupuestoMensual, Long> {
    @Query(value = "SELECT * FROM \"PresupuestoMensual\"", nativeQuery = true)
    List<PresupuestoMensual> findAllNative();
    
    @Modifying
    @Transactional
    @Query("UPDATE PresupuestoMensual p SET p.activo = false WHERE p.presupuestoId != :presupuestoId")
    void desactivarOtrosPresupuestos(@Param("presupuestoId") Long presupuestoId);
    
    @Modifying
    @Transactional
    @Query("UPDATE PresupuestoMensual p SET p.activo = true WHERE p.presupuestoId = :presupuestoId")
    void activarPresupuesto(@Param("presupuestoId") Long presupuestoId);
    
    @Query("SELECT p FROM PresupuestoMensual p WHERE p.activo = true ORDER BY p.anio DESC, p.mes DESC")
    Optional<PresupuestoMensual> findFirstByActivoTrueOrderByAnioDescMesDesc();
}
