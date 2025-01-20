package com.example.trendmart.services.product;

import java.util.List;
import java.util.UUID;

import com.example.trendmart.entities.Product;
import com.example.trendmart.requests.AddProductRequest;
import com.example.trendmart.requests.ProductUpdateRequest;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    List<Product> getAllProducts();

    Product getProductById(UUID id);

    void deleteProductById(UUID id);

    Product updateProductById(ProductUpdateRequest product, UUID id);

    List<Product>getProductsByCategoryName(String category);

    List<Product>getProductsByBrand(String brand);

    List<Product>getProductsByCategoryAndBrand(String category,String brand);

    List<Product>getProductByName(String name);

    List<Product>getProductsByBrandAndName(String brand,String name);

    Long countProductsByBrandAndName(String brand,String name);
}
