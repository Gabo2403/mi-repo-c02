package com.project.demo.rest.product;

import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Product>> listarTodos() {
        return ResponseEntity.ok(productRepository.findAll());
    }


    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Product> adicionar(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Product> actualizar(@PathVariable Long id, @RequestBody Product producto) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setNombre(producto.getNombre());
                    existingProduct.setDescripcion(producto.getDescripcion());
                    existingProduct.setPrecio(producto.getPrecio());
                    existingProduct.setCantidadEnStock(producto.getCantidadEnStock());
                    existingProduct.setCategoria(producto.getCategoria());
                    return ResponseEntity.ok(productRepository.save(existingProduct));
                })
                .orElseGet(() -> {
                    producto.setId(id);
                    return ResponseEntity.ok(productRepository.save(producto));
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
