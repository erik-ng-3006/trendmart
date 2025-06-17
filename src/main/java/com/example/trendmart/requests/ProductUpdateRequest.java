package com.example.trendmart.requests;

import java.math.BigDecimal;

import com.example.trendmart.entities.Category;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    Long id;

    String name;

    String brand;

    String description;

    BigDecimal price;

    int inventory;
    
    Category category;
}
