package com.example.trendmart.controllers;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trendmart.entities.Category;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.category.ICategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Categories", description = "APIs for managing categories")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
  private final ICategoryService categoryService;

  @Operation(summary = "Get all categories")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Categories fetched successfully"),
      @ApiResponse(responseCode = "500", description = "Error")
  })
  @GetMapping("/all")
  public ResponseEntity<CustomApiResponse> getAll() {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Categories fetched successfully", categoryService.getAllCategories()));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse("Error", INTERNAL_SERVER_ERROR));
    }
  }

  @Operation(summary = "Add a category")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category added successfully"),
      @ApiResponse(responseCode = "409", description = "Category already exists")
  })
  @PostMapping("/add")
  public ResponseEntity<CustomApiResponse> addCategory(@RequestBody Category category) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Category added successfully", categoryService.addCategory(category)));
    } catch (Exception e) {
      return ResponseEntity.status(CONFLICT).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Get a category by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<CustomApiResponse> getCategoryById(@PathVariable UUID id) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Category fetched successfully", categoryService.getCategoryById(id)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Get a category by name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @GetMapping("/name/{name}")
  public ResponseEntity<CustomApiResponse> getCategoryByName(@PathVariable String name) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Category fetched successfully", categoryService.getCategoryByName(name)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Delete a category by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<CustomApiResponse> deleteCategoryById(@PathVariable UUID id) {
    try {
      categoryService.deleteCategoryById(id);
      return ResponseEntity.ok(new CustomApiResponse("Category deleted successfully", null));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Update a category by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category updated successfully"),
      @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @PostMapping("/update/{id}")
  public ResponseEntity<CustomApiResponse> updateCategory(@PathVariable UUID id, @RequestBody Category category) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Category updated successfully", categoryService.updateCategory(category, id)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

}