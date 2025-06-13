package com.project.demo.rest.product;

import com.project.demo.logic.entity.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    public List<Product> listar() {
        return productService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Product crear(@RequestBody Product producto) {
        return productService.save(producto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Product> actualizar(@PathVariable Long id, @RequestBody Product producto) {
        return productService.findById(id)
                .map(existingProduct -> {
                    existingProduct.setNombre(producto.getNombre());
                    existingProduct.setDescripcion(producto.getDescripcion());
                    existingProduct.setPrecio(producto.getPrecio());
                    existingProduct.setCantidadEnStock(producto.getCantidadEnStock());
                    return ResponseEntity.ok(productService.save(existingProduct));
                })
                .orElseGet(() -> {
                    producto.setId(id);
                    return ResponseEntity.ok(productService.save(producto));
                });
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void eliminar(@PathVariable Long id) {
        productService.deleteById(id);
    }
}

