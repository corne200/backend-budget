package com.example.backendbudget.controller;

import com.example.backendbudget.model.Gastos;
import com.example.backendbudget.service.GastosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://front-budget.netlify.app")
@RestController
@RequestMapping("/api/gastos")
public class GastosController {
    
    @Autowired
    private GastosService gastosService;

    /**
     * Obtiene todos los gastos
     */
    @GetMapping("/todos")
    public ResponseEntity<List<Gastos>> obtenerTodosLosGastos() {
        List<Gastos> gastos = gastosService.obtenerTodos();
        return ResponseEntity.ok(gastos);
    }

    /**
     * Obtiene un gasto por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gastos> obtenerGastoPorId(@PathVariable Long id) {
        Optional<Gastos> gasto = gastosService.obtenerPorId(id);
        return gasto.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene gastos por presupuesto ID
     */
    @GetMapping("/presupuesto/{presupuestoId}")
    public ResponseEntity<List<Gastos>> getGastosByPresupuestoId(@PathVariable Long presupuestoId) {
        List<Gastos> gastos = gastosService.findByPresupuestoId(presupuestoId);
        return ResponseEntity.ok(gastos);
    }

    /**
     * Obtiene gastos por categoría
     */
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Gastos>> obtenerGastosPorCategoria(@PathVariable Long categoriaId) {
        List<Gastos> gastos = gastosService.obtenerGastosPorCategoria(categoriaId);
        return ResponseEntity.ok(gastos);
    }

    /**
     * Registra un nuevo gasto
     */
    @PostMapping("/registrarGasto")
    public ResponseEntity<Gastos> registrarGasto(@RequestBody Gastos gasto) {
        try {
            Gastos nuevoGasto = gastosService.registrarGasto(gasto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoGasto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza un gasto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gastos> actualizarGasto(@PathVariable Long id, @RequestBody Gastos gasto) {
        try {
            Optional<Gastos> gastoActualizado = gastosService.actualizarGasto(id, gasto);
            return gastoActualizado.map(ResponseEntity::ok)
                                  .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un gasto por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        if (gastosService.eliminarGasto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verifica si existe un gasto
     */
    @GetMapping("/existe/{id}")
    public ResponseEntity<Boolean> existeGasto(@PathVariable Long id) {
        boolean existe = gastosService.existeGasto(id);
        return ResponseEntity.ok(existe);
    }

    /**
     * Cuenta el total de gastos
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarGastos() {
        long total = gastosService.contarGastos();
        return ResponseEntity.ok(total);
    }

    /**
     * Calcula el total de gastos por presupuesto
     */
    @GetMapping("/total/presupuesto/{presupuestoId}")
    public ResponseEntity<BigDecimal> calcularTotalGastosPorPresupuesto(@PathVariable Long presupuestoId) {
        BigDecimal total = gastosService.calcularTotalGastosPorPresupuesto(presupuestoId);
        return ResponseEntity.ok(total);
    }

    /**
     * Obtiene gastos por rango de fechas
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Gastos>> obtenerGastosPorRangoFechas(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            List<Gastos> gastos = gastosService.obtenerGastosPorRangoFechas(inicio, fin);
            return ResponseEntity.ok(gastos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
