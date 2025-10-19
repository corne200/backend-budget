package com.example.backendbudget.service;

import com.example.backendbudget.model.PresupuestoMensual;
import com.example.backendbudget.repository.PresupuestoMensualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PresupuestoMensualService {

    @Autowired
    private PresupuestoMensualRepository presupuestoMensualRepository;

    public Optional<PresupuestoMensual> obtenerPresupuestoActivo() {
        return presupuestoMensualRepository.findFirstByActivoTrueOrderByAnioDescMesDesc();
    }

    @Transactional
    public ResponseEntity<?> activarPresupuesto(Long presupuestoId) {
        try {
            // Primero desactivamos todos los presupuestos
            presupuestoMensualRepository.desactivarOtrosPresupuestos(presupuestoId);
            
            // Luego activamos el presupuesto seleccionado
            presupuestoMensualRepository.activarPresupuesto(presupuestoId);
            
            return ResponseEntity.ok().body("Presupuesto activado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al activar el presupuesto: " + e.getMessage());
        }
    }
}
