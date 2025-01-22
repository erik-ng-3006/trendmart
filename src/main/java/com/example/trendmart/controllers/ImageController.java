package com.example.trendmart.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.trendmart.dtos.ImageDTO;
import com.example.trendmart.entities.Image;
import com.example.trendmart.exceptions.ResourceNotFoundException;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.image.IImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Images", description = "APIs for managing images")
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
    public ResponseEntity<CustomApiResponse> saveImages(@RequestParam("files") List<MultipartFile> files, 
                                                        @RequestParam("productId") UUID productId) {
        try {
            List<ImageDTO> savedImageDTO = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new CustomApiResponse("Images saved successfully", savedImageDTO));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                                .body(new CustomApiResponse("Failed to save images", e.getMessage()));
        }
    }

    @Operation(summary = "Download an image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image downloaded successfully"),
        @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping("/image/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable UUID id) throws SQLException {
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
    public ResponseEntity<CustomApiResponse> updateImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        try {
            Image image = imageService.getImageById(id);

            if (image != null) {
                imageService.updateImage(file, id);
                return ResponseEntity.ok(new CustomApiResponse("Image updated successfully", null));
            } 
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse("Failed to update image", INTERNAL_SERVER_ERROR));
    }

    @Operation(summary = "Delete an image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Image not found"),
        @ApiResponse(responseCode = "500", description = "Failed to delete image")
    })
    @DeleteMapping("/image/{id}/delete")
    public ResponseEntity<CustomApiResponse> deleteImage(@PathVariable UUID id) {
        try {
            Image image = imageService.getImageById(id);

            if (image != null) {
                imageService.deleteImageById(id);
                return ResponseEntity.ok(new CustomApiResponse("Image deleted successfully", null));
            } 
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse("Failed to delete image", INTERNAL_SERVER_ERROR));
    }    
}