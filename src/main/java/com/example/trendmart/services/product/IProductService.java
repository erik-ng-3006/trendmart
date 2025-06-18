package com.example.trendmart.services.product;

import java.util.List;

import com.example.trendmart.dtos.ProductDTO;
import com.example.trendmart.entities.Product;
import com.example.trendmart.requests.AddProductRequest;
import com.example.trendmart.requests.ProductUpdateRequest;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProductById(ProductUpdateRequest product, Long id);

    List<Product>getProductsByCategoryName(String category);

    List<Product>getProductsByBrand(String brand);

    List<Product>getProductsByCategoryAndBrand(String category,String brand);

    List<Product>getProductByName(String name);

    List<Product>getProductsByBrandAndName(String brand,String name);

    Long countProductsByBrandAndName(String brand,String name);

    List<ProductDTO> getConvertedProducts(List<Product> products);

    ProductDTO convertToDto(Product product);
}
