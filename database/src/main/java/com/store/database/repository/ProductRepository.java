package com.store.database.repository;

import com.store.database.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    List<Product> findByStockQtyLessThan(int stockQty);
}