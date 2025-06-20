package com.example.trendmart.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String fileName;

    String fileType;

    @Lob
    Blob image;

    String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
