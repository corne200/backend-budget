package com.example.backendbudget.service;

import com.example.backendbudget.model.Gastos;
import com.example.backendbudget.model.Categorias;
import com.example.backendbudget.model.PresupuestoMensual;
import com.example.backendbudget.repository.GastosRepository;
import com.example.backendbudget.repository.CategoriasRepository;
import com.example.backendbudget.repository.PresupuestoMensualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GastosService {
    
    @Autowired
    private GastosRepository gastosRepository;
    
    @Autowired
    private CategoriasRepository categoriasRepository;
    
    @Autowired
    private PresupuestoMensualRepository presupuestoMensualRepository;

    /**
     * Obtiene todos los gastos
     */
    public List<Gastos> obtenerTodos() {
        return gastosRepository.findAll();
    }

    /**
     * Obtiene un gasto por su ID
     */
    public Optional<Gastos> obtenerPorId(Long id) {
        return gastosRepository.findById(id);
    }

    /**
     * Obtiene gastos por presupuesto ID
     */
    public List<Gastos> findByPresupuestoId(Long presupuestoId) {
        return gastosRepository.findByPresupuesto_PresupuestoId(presupuestoId);
    }

    /**
     * Registra un nuevo gasto
     */
    public Gastos registrarGasto(Gastos gasto) {
        // Validar que la categoría existe
        if (gasto.getCategoriaId() != null) {
            Optional<Categorias> categoria = categoriasRepository.findById(gasto.getCategoriaId());
            if (categoria.isPresent()) {
                gasto.setCategoria(categoria.get());
            } else {
                throw new RuntimeException("Categoría no encontrada con ID: " + gasto.getCategoriaId());
            }
        }

        // Validar que el presupuesto existe
        if (gasto.getPresupuestoId() != null) {
            Optional<PresupuestoMensual> presupuesto = presupuestoMensualRepository.findById(gasto.getPresupuestoId());
            if (presupuesto.isPresent()) {
                gasto.setPresupuesto(presupuesto.get());
            } else {
                throw new RuntimeException("Presupuesto no encontrado con ID: " + gasto.getPresupuestoId());
            }
        }

        // Si no se especifica fecha, usar la fecha actual
        if (gasto.getFecha() == null) {
            gasto.setFecha(LocalDate.now());
        }

        return gastosRepository.save(gasto);
    }

    /**
     * Actualiza un gasto existente
     */
    public Optional<Gastos> actualizarGasto(Long id, Gastos gastoActualizado) {
        return gastosRepository.findById(id)
            .map(gasto -> {
                // Actualizar campos básicos
                if (gastoActualizado.getMonto() != null) {
                    gasto.setMonto(gastoActualizado.getMonto());
                }
                if (gastoActualizado.getDescripcion() != null) {
                    gasto.setDescripcion(gastoActualizado.getDescripcion());
                }
                if (gastoActualizado.getFecha() != null) {
                    gasto.setFecha(gastoActualizado.getFecha());
                }

                // Actualizar categoría si se proporciona
                if (gastoActualizado.getCategoriaId() != null) {
                    Optional<Categorias> categoria = categoriasRepository.findById(gastoActualizado.getCategoriaId());
                    if (categoria.isPresent()) {
                        gasto.setCategoria(categoria.get());
                        gasto.setCategoriaId(gastoActualizado.getCategoriaId());
                    }
                }

                // Actualizar presupuesto si se proporciona
                if (gastoActualizado.getPresupuestoId() != null) {
                    Optional<PresupuestoMensual> presupuesto = presupuestoMensualRepository.findById(gastoActualizado.getPresupuestoId());
                    if (presupuesto.isPresent()) {
                        gasto.setPresupuesto(presupuesto.get());
                        gasto.setPresupuestoId(gastoActualizado.getPresupuestoId());
                    }
                }

                return gastosRepository.save(gasto);
            });
    }

    /**
     * Elimina un gasto por su ID
     */
    public boolean eliminarGasto(Long id) {
        if (gastosRepository.existsById(id)) {
            gastosRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Verifica si existe un gasto con el ID especificado
     */
    public boolean existeGasto(Long id) {
        return gastosRepository.existsById(id);
    }

    /**
     * Cuenta el total de gastos
     */
    public long contarGastos() {
        return gastosRepository.count();
    }

    /**
     * Calcula el total de gastos por presupuesto
     */
    public BigDecimal calcularTotalGastosPorPresupuesto(Long presupuestoId) {
        List<Gastos> gastos = findByPresupuestoId(presupuestoId);
        return gastos.stream()
            .map(Gastos::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtiene gastos por rango de fechas
     */
    public List<Gastos> obtenerGastosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return gastosRepository.findAll().stream()
            .filter(gasto -> {
                LocalDate fecha = gasto.getFecha();
                return fecha != null && 
                       !fecha.isBefore(fechaInicio) && 
                       !fecha.isAfter(fechaFin);
            })
            .toList();
    }

    /**
     * Obtiene gastos por categoría
     */
    public List<Gastos> obtenerGastosPorCategoria(Long categoriaId) {
        return gastosRepository.findAll().stream()
            .filter(gasto -> gasto.getCategoriaId() != null && 
                           gasto.getCategoriaId().equals(categoriaId))
            .toList();
    }
}
