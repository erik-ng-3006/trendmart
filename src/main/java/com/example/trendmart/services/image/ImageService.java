package com.example.trendmart.services.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.trendmart.dtos.ImageDTO;
import com.example.trendmart.entities.Image;
import com.example.trendmart.entities.Product;
import com.example.trendmart.exceptions.ResourceNotFoundException;
import com.example.trendmart.repositories.IImageRepository;
import com.example.trendmart.services.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
  private final IImageRepository imageRepository;
  private final IProductService productService;
  @Override
  public Image getImageById(Long id) {
    return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
  }

  @Override
  public void deleteImageById(Long id) {
    imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
      throw new ResourceNotFoundException("Image not found with id: " + id);
    }); 
  }

  @Override
  public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
    Product product = productService.getProductById(productId);
    List<ImageDTO> savedImageDTO = new ArrayList<>();

    for (MultipartFile file : files) {
      try {
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(new SerialBlob(file.getBytes()));
        image.setProduct(product);

        String buildDownloadUrl = "/api/v1/images/image/download/";
        String downloadUrl = buildDownloadUrl + image.getId();
        image.setDownloadUrl(downloadUrl);

        Image savedImage = imageRepository.save(image);

        savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

        imageRepository.save(savedImage);

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImageId(savedImage.getId());
        imageDTO.setImageName(savedImage.getFileName());
        imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
        savedImageDTO.add(imageDTO);

      } catch (IOException | SQLException e) {
        throw new RuntimeException(e.getMessage());
      }
    }

    return savedImageDTO;
  }

  @Override
  public void updateImage(MultipartFile file, Long id) {
    Image image = getImageById(id);

    try {
      image.setFileName(file.getOriginalFilename());
      image.setFileType(file.getContentType());
      image.setImage(new SerialBlob(file.getBytes()));
      imageRepository.save(image);
    } catch (IOException | SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
