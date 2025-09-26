package com.example.backendbudget.service;

import com.example.backendbudget.model.Categorias;
import com.example.backendbudget.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriasService {
    @Autowired
    private CategoriasRepository categoriasRepository;

    public List<Categorias> obtenerTodas() {
        return categoriasRepository.findAll().stream()
            .map(c -> {
                Categorias cat = new Categorias();
                cat.setCategoriaId(c.getCategoriaId());
                cat.setNombre(c.getNombre());
                // No incluir gastos para evitar referencias circulares
                return cat;
            })
            .collect(Collectors.toList());
    }

    public Optional<Categorias> obtenerPorId(Long id) {
        Optional<Categorias> categoria = categoriasRepository.findById(id);
        if (categoria.isPresent()) {
            Categorias cat = new Categorias();
            cat.setCategoriaId(categoria.get().getCategoriaId());
            cat.setNombre(categoria.get().getNombre());
            return Optional.of(cat);
        }
        return Optional.empty();
    }

    public Categorias crearCategoria(Categorias categoria) {
        // Limpiar gastos antes de guardar para evitar problemas
        categoria.setGastos(null);
        return categoriasRepository.save(categoria);
    }

    public Optional<Categorias> actualizarCategoria(Long id, Categorias categoriaActualizada) {
        return categoriasRepository.findById(id)
            .map(categoria -> {
                categoria.setNombre(categoriaActualizada.getNombre());
                return categoriasRepository.save(categoria);
            });
    }

    public boolean eliminarCategoria(Long id) {
        if (categoriasRepository.existsById(id)) {
            categoriasRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existeCategoria(Long id) {
        return categoriasRepository.existsById(id);
    }

    public long contarCategorias() {
        return categoriasRepository.count();
    }
}
