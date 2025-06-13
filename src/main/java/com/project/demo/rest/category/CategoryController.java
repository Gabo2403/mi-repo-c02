package com.project.demo.rest.category;

import com.project.demo.logic.entity.category.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    public List<Category> listar() {
        return categoryService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Category crear(@RequestBody Category categoria) {
        return categoryService.save(categoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Category> actualizar(@PathVariable Long id, @RequestBody Category categoria) {
        return categoryService.findById(id)
                .map(existingCategory -> {
                    existingCategory.setNombre(categoria.getNombre());
                    existingCategory.setDescripcion(categoria.getDescripcion());
                    return ResponseEntity.ok(categoryService.save(existingCategory));
                })
                .orElseGet(() -> {
                    categoria.setId(id);
                    return ResponseEntity.ok(categoryService.save(categoria));
                });
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void eliminar(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
