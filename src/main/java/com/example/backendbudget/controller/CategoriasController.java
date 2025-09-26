package com.example.backendbudget.controller;

import com.example.backendbudget.model.Categorias;
import com.example.backendbudget.service.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {
    @Autowired
    private CategoriasService categoriasService;

    @GetMapping("/todas")
    public ResponseEntity<List<Categorias>> obtenerTodasLasCategorias() {
        List<Categorias> categorias = categoriasService.obtenerTodas();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarCategorias() {
        long total = categoriasService.contarCategorias();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<Boolean> existeCategoria(@PathVariable Long id) {
        boolean existe = categoriasService.existeCategoria(id);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorias> obtenerCategoriaPorId(@PathVariable Long id) {
        Optional<Categorias> categoria = categoriasService.obtenerPorId(id);
        return categoria.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categorias> crearCategoria(@RequestBody Categorias categoria) {
        try {
            Categorias nuevaCategoria = categoriasService.crearCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorias> actualizarCategoria(@PathVariable Long id, @RequestBody Categorias categoria) {
        Optional<Categorias> categoriaActualizada = categoriasService.actualizarCategoria(id, categoria);
        return categoriaActualizada.map(ResponseEntity::ok)
                                  .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (categoriasService.eliminarCategoria(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
