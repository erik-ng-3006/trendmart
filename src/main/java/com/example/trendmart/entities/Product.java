package com.example.trendmart.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    String brand;

    String description;

    BigDecimal price;

    int inventory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<Image> images;

    public Product(String brand, String description, int inventory, String name, BigDecimal price, Category category) {
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.inventory = inventory;
        this.name = name;
        this.price = price;
    }
}
