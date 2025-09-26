package com.example.backendbudget.service;

import com.example.backendbudget.model.PresupuestoMensual;
import com.example.backendbudget.repository.PresupuestoMensualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PresupuestoMensualService {

    @Autowired
    private PresupuestoMensualRepository presupuestoMensualRepository;

    public Optional<PresupuestoMensual> obtenerPresupuestoActivo() {
        return presupuestoMensualRepository.findFirstByActivoTrueOrderByAnioDescMesDesc();
    }
}
