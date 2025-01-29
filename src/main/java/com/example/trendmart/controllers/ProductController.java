package com.example.trendmart.controllers;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.trendmart.entities.Product;
import com.example.trendmart.requests.AddProductRequest;
import com.example.trendmart.requests.ProductUpdateRequest;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.product.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Products", description = "APIs for managing products")
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
  private final IProductService productService;

  @Operation(summary = "Get all products")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
      @ApiResponse(responseCode = "500", description = "Error") 
  })
  @GetMapping("/all")
  public ResponseEntity<CustomApiResponse> getAll() {
    return ResponseEntity.ok(new CustomApiResponse("Products fetched successfully", productService.getAllProducts()));
  }

  @Operation(summary = "Get a product by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<CustomApiResponse> getProductById(@PathVariable UUID id) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productService.getProductById(id)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Add a product")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product added successfully"),
      @ApiResponse(responseCode = "500", description = "Error")
  })
  @PostMapping("/add")
  public ResponseEntity<CustomApiResponse> addProduct(@RequestBody AddProductRequest product) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Product added successfully", productService.addProduct(product)));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Update a product by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product updated successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @PutMapping("/update/{id}")
  public ResponseEntity<CustomApiResponse> updateProduct(@PathVariable UUID id, @RequestBody ProductUpdateRequest product) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Product updated successfully", productService.updateProductById(product, id)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Delete a product by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<CustomApiResponse> deleteProductById(@PathVariable UUID id) {
    try {
      productService.deleteProductById(id);
      return ResponseEntity.ok(new CustomApiResponse("Product deleted successfully", null));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Get a product by brand and name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/by-brand-and-name")
  public ResponseEntity<CustomApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
    try {
      List<Product> products = productService.getProductsByBrandAndName(brand, name);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse("Product not found", null));
      } else {
        return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", products));
      }
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Get a product by category and brand")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/by-category-and-brand")
  public ResponseEntity<CustomApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brand) {
    try {
      List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brand);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse("Product not found", null));
      } else {
        return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", products));
      }
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Get a product by name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/name/{name}")
  public ResponseEntity<CustomApiResponse> getProductByName(@PathVariable String name) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productService.getProductByName(name)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @Operation(summary = "Get a product by brand")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/brand/{brand}")
  public ResponseEntity<CustomApiResponse> getProductByBrand(@PathVariable String brand) {
    try {
      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productService.getProductsByBrand(brand)));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

}