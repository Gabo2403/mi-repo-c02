package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

