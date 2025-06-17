package com.example.trendmart.services.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.trendmart.dtos.ImageDTO;
import com.example.trendmart.entities.Image;

public interface IImageService {
  Image getImageById(Long id);

  void deleteImageById(Long id);
  
  List<ImageDTO> saveImage(List<MultipartFile> files, Long productId);

  void updateImage(MultipartFile file, Long id);
}
