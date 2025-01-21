package com.example.trendmart.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trendmart.entities.Image;

public interface  IImageRepository extends JpaRepository<Image, UUID> {
  
}
