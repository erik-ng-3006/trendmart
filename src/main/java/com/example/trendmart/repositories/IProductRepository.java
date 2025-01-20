package com.example.trendmart.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trendmart.entities.Product;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategoryName(String categoryName);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String categoryName, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);
}
