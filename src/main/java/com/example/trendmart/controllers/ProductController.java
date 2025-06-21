package com.example.trendmart.controllers;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.trendmart.dtos.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.trendmart.entities.Product;
import com.example.trendmart.requests.AddProductRequest;
import com.example.trendmart.requests.ProductUpdateRequest;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.services.product.IProductService;

import io.swagger.v3.oas.annotations.Operation;
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
    List<Product> products =  productService.getAllProducts();

    List<ProductDTO> productDTOs = productService.getConvertedProducts(products);

    return ResponseEntity.ok(new CustomApiResponse("Products fetched successfully", productDTOs));
  }

  @Operation(summary = "Get a product by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<CustomApiResponse> getProductById(@PathVariable Long id) {
    try {
      Product product = productService.getProductById(id);

      ProductDTO productDto = productService.convertToDto(product);

      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productDto));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Add a product")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product added successfully"),
      @ApiResponse(responseCode = "500", description = "Error")
  })
  @PostMapping("/add")
  public ResponseEntity<CustomApiResponse> addProduct(@RequestBody AddProductRequest product) {
    try {
      Product theProduct = productService.addProduct(product);

      ProductDTO productDto = productService.convertToDto(theProduct);

      return ResponseEntity.ok(new CustomApiResponse("Product added successfully", productDto));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Update a product by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product updated successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @PutMapping("/update/{id}")
  public ResponseEntity<CustomApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
    try {
      Product theProduct = productService.updateProductById(request, id);

      ProductDTO productDto = productService.convertToDto(theProduct);

      return ResponseEntity.ok(new CustomApiResponse("Product updated successfully", productDto));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Operation(summary = "Delete a product by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<CustomApiResponse> deleteProductById(@PathVariable Long id) {
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
      }

      List<ProductDTO> productDTOs = productService.getConvertedProducts(products);

      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productDTOs));

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
      }

      List<ProductDTO> productDTOs = productService.getConvertedProducts(products);

      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productDTOs));

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
      List <Product> products = productService.getProductByName(name);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse("Product not found", null));
      }

      List<ProductDTO> productDTOs = productService.getConvertedProducts(products);

      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productDTOs));
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
      List<Product> products = productService.getProductsByBrand(brand);
      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse("No products found ", null));
      }

      List<ProductDTO> productDTOs = productService.getConvertedProducts(products);

      return ResponseEntity.ok(new CustomApiResponse("Product fetched successfully", productDTOs));
    } catch (Exception e) {
      return ResponseEntity.status(NOT_FOUND).body(new CustomApiResponse(e.getMessage(), null));
    }
  }

}