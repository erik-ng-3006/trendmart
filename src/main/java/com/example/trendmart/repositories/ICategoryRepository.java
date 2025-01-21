package com.example.trendmart.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trendmart.entities.Category;

public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String name);

    boolean existsByName(String name);
}
