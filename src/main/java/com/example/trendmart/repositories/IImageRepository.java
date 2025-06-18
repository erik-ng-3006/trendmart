package com.example.trendmart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trendmart.entities.Image;

import java.util.List;

public interface  IImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
