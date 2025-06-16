package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByStockGreaterThan(Integer stock);

    List<Product> findByStockEquals(Integer stock);

    void deleteByCategory(Category category);

    long countByCategory(Category category);

    List<Product> findByCategoryAndStockGreaterThan(Category category, Integer stock);

}

