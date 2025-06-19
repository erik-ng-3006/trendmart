package com.example.trendmart.controllers;

import java.sql.SQLException;
import java.util.List;

import com.example.trendmart.dtos.ImageDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import com.example.trendmart.entities.Image;
import com.example.trendmart.exceptions.ResourceNotFoundException;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.image.IImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Images", description = "APIs for managing images")
@Tag(name = "Images", description = "APIs for managing product images")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @Operation(summary = "Upload multiple images for a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Images saved successfully"),
        @ApiResponse(responseCode = "500", description = "Failed to save images")
    })
    @PostMapping("/upload")
    public ResponseEntity<CustomApiResponse> saveImages(
            @Parameter(description = "Image files to upload") @RequestParam("files") List<MultipartFile> files,
            @Parameter(description = "ID of the product to associate with the images") @RequestParam("productId") Long productId) {
        try {
            List<ImageDTO> savedImageDTO = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new CustomApiResponse("Images saved successfully", savedImageDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomApiResponse("Failed to save images", e.getMessage()));
        }
    }

    @Operation(summary = "Download an image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image downloaded successfully"),
        @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping("/image/download/{id}")
    public ResponseEntity<Resource> downloadImage(
            @Parameter(description = "ID of the image") @PathVariable Long id) throws SQLException {
        Image image = imageService.getImageById(id);

        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @Operation(summary = "Update an image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image updated successfully"),
        @ApiResponse(responseCode = "404", description = "Image not found"),
        @ApiResponse(responseCode = "500", description = "Failed to update image")
    })
    @PutMapping("/image/{id}/update")
    public ResponseEntity<CustomApiResponse> updateImage(
            @Parameter(description = "ID of the image to update") @PathVariable Long id,
            @Parameter(description = "Image file to update") @RequestParam("file") MultipartFile file) {
        try {
            Image image = imageService.getImageById(id);

            if (image != null) {
                imageService.updateImage(file, id);
                return ResponseEntity.ok(new CustomApiResponse("Image updated successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomApiResponse("Failed to update image", null));
    }

    @Operation(summary = "Delete an image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Image not found"),
        @ApiResponse(responseCode = "500", description = "Failed to delete image")
    })
    @DeleteMapping("/image/{id}/delete")
    public ResponseEntity<CustomApiResponse> deleteImage(
            @Parameter(description = "ID of the image to delete") @PathVariable Long id) {
        try {
            Image image = imageService.getImageById(id);

            if (image != null) {
                imageService.deleteImageById(id);
                return ResponseEntity.ok(new CustomApiResponse("Image deleted successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomApiResponse("Failed to delete image", null));
    }    
}