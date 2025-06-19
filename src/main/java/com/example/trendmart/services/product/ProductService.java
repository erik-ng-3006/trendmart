package com.example.trendmart.services.product;

import java.util.List;
import java.util.Optional;

import com.example.trendmart.dtos.ImageDTO;
import com.example.trendmart.dtos.ProductDTO;
import com.example.trendmart.repositories.IImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.trendmart.entities.Category;
import com.example.trendmart.entities.Product;
import com.example.trendmart.exceptions.ProductNotFoundException;
import com.example.trendmart.repositories.ICategoryRepository;
import com.example.trendmart.repositories.IProductRepository;
import com.example.trendmart.requests.AddProductRequest;
import com.example.trendmart.requests.ProductUpdateRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final IImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, create a new category and save it as a new category
        // The set as the new product category.

        if (productExists(request.getName(), request.getBrand())) {
            throw new RuntimeException("Product already exists");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    // helper method to create a product
    private Product createProduct(AddProductRequest request,Category category) {
        return new Product(request.getBrand(), request.getDescription(), request.getInventory(), request.getName(), request.getPrice(), category);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException("Product not found");
                        });

    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String categoryName, String brand) {
        return productRepository.findByCategoryNameAndBrand(categoryName, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDTO convertToDto(Product product) {
        ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
        if (product.getImages() != null) {
            List<ImageDTO> imageDTOs = product.getImages().stream()
                    .map(image -> modelMapper.map(image, ImageDTO.class))
                    .toList();
            productDto.setImages(imageDTOs);
        }
        return productDto;
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }
}
