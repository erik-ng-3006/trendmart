package com.example.trendmart.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ImageDTO {
  private UUID imageId;

  private String imageName;

  private String downloadUrl;
}
