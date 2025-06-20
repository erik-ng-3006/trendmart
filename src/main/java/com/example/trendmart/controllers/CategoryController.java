package com.example.trendmart.controllers;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trendmart.entities.Category;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.category.ICategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Categories", description = "APIs for managing categories")
@Tag(name = "Categories", description = "APIs for managing product categories")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
  private final ICategoryService categoryService;

  @Operation(summary = "Get all categories")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
  @GetMapping("/all")
  public ResponseEntity<CustomApiResponse> getAllCategories() {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Categories fetched successfully", categoryService.getAllCategories()));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse("Error", INTERNAL_SERVER_ERROR));
    }
  }

  @Operation(summary = "Add a new category")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category added successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @PostMapping("/add")
  public ResponseEntity<CustomApiResponse> addCategory(
          @Parameter(description = "Category details") @RequestBody Category categoryDTO) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Category added successfully", categoryService.addCategory(categoryDTO)));
    } catch (Exception e) {
      return ResponseEntity.status(CONFLICT).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Get category by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category found"),
      @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @GetMapping("/{categoryId}")
  public ResponseEntity<CustomApiResponse> getCategory(
          @Parameter(description = "ID of the category to retrieve") @PathVariable Long categoryId) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Category fetched successfully", categoryService.getCategoryById(categoryId)));
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

  @Operation(summary = "Delete a category")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Category not found")
  })
  @DeleteMapping("/{categoryId}/delete")
  public ResponseEntity<CustomApiResponse> deleteCategory(
          @Parameter(description = "ID of the category to delete") @PathVariable Long categoryId) {
    try {
      categoryService.deleteCategoryById(categoryId);
      return ResponseEntity.ok(new CustomApiResponse("Category deleted successfully", null));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Update a category")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Category updated successfully"),
      @ApiResponse(responseCode = "404", description = "Category not found"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @PutMapping("/{categoryId}/update")
  public ResponseEntity<CustomApiResponse> updateCategory(
          @Parameter(description = "ID of the category to update") @PathVariable Long categoryId, 
          @Parameter(description = "Updated category details") @RequestBody Category category) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Category updated successfully", categoryService.updateCategory(category, categoryId)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

}