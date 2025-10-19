package com.example.backendbudget.controller;

import com.example.backendbudget.model.Categorias;
import com.example.backendbudget.model.Gastos;
import com.example.backendbudget.model.PresupuestoMensual;
import com.example.backendbudget.model.PresupuestoMensualDTO;
import com.example.backendbudget.repository.PresupuestoMensualRepository;
import com.example.backendbudget.service.CategoriasService;
import com.example.backendbudget.service.GastosService;
import com.example.backendbudget.service.PresupuestoMensualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://front-budget.netlify.app"})
@RestController
@RequestMapping("/presupuestos")
public class PresupuestoMensualController {
    @Autowired
    private PresupuestoMensualRepository presupuestoMensualRepository;

    @Autowired
    private CategoriasService categoriasService;

    @Autowired
    private GastosService gastosService;

    @Autowired
    private PresupuestoMensualService presupuestoMensualService;

    @PostMapping("/crear")
    public PresupuestoMensual crearPresupuesto(@RequestBody PresupuestoMensualDTO dto) {
        PresupuestoMensual p = new PresupuestoMensual();
        p.setAnio(dto.getAnio());
        p.setMes(dto.getMes());
        p.setSueldo(dto.getSueldo());
        p.setFechaCreacion(LocalDate.now());
        p.setActivo(false);
        p.setGastos(new ArrayList<>());
        return presupuestoMensualRepository.save(p);
    }

    // ... existing code ...

    @PutMapping("/activar/{presupuestoId}")
    public ResponseEntity<?> activarPresupuesto(@PathVariable Long presupuestoId) {
        return presupuestoMensualService.activarPresupuesto(presupuestoId);
    }

    @GetMapping("/obtenerPresupuesto")
    public List<PresupuestoMensual> getAllPresupuestos() {
        List<PresupuestoMensual> presupuestos = presupuestoMensualRepository.findAll();
        presupuestos.forEach(p -> p.setGastos(null)); // Evitar referencia circular
        System.out.println("Presupuestos mensuales: " + presupuestos);
        return  presupuestos;
    }

    @GetMapping("/obtenerPresupuestoNative")
    public List<PresupuestoMensual> getAllPresupuestosNative() {
        List<PresupuestoMensual> presupuestos = presupuestoMensualRepository.findAllNative();
        System.out.println("Presupuestos mensuales (native): " + presupuestos);
        return presupuestos;
    }

    @GetMapping("/obtenerCategorias")
    public List<Categorias> obtenerCategorias() {
        return categoriasService.obtenerTodas();
    }

    @GetMapping("/obtenerGastosPorPresupuesto/{presupuestoId}")
    public List<Gastos> getGastosByPresupuestoId(@PathVariable Long presupuestoId) {
        List<Gastos> gastos = gastosService.findByPresupuestoId(presupuestoId);
        for (Gastos gasto : gastos) {
            gasto.setCategoria(null); // Evitar referencia circular
            gasto.setPresupuesto(null); // Evitar referencia circular
        }
        return gastos;
    }

    @GetMapping("/activo")
    public ResponseEntity<PresupuestoMensual> obtenerPresupuestoActivo() {
        return presupuestoMensualRepository
                .findFirstByActivoTrueOrderByAnioDescMesDesc()
                .map(p -> {
                    p.setGastos(null); // Evitar referencia circular
                    return ResponseEntity.ok(p);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
