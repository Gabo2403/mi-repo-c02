package com.project.demo.rest.category;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    public List<Category> listar() {
        return categoryRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Category crear(@RequestBody Category categoria) {
        return categoryRepository.save(categoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Category> actualizar(@PathVariable Long id, @RequestBody Category categoria) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setNombre(categoria.getNombre());
                    existingCategory.setDescripcion(categoria.getDescripcion());
                    return ResponseEntity.ok(categoryRepository.save(existingCategory));
                })
                .orElseGet(() -> {
                    categoria.setId(id);
                    return ResponseEntity.ok(categoryRepository.save(categoria));
                });
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void eliminar(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
