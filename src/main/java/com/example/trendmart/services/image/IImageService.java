package com.example.trendmart.services.image;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.trendmart.dtos.ImageDTO;
import com.example.trendmart.entities.Image;

public interface IImageService {
  Image getImageById(UUID id);

  void deleteImageById(UUID id);
  
  List<ImageDTO> saveImage(List<MultipartFile> files, UUID productId);

  void updateImage(MultipartFile file, UUID id);
}
