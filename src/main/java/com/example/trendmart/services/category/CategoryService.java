package com.example.trendmart.services.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.trendmart.entities.Category;
import com.example.trendmart.exceptions.AlreadyExistException;
import com.example.trendmart.exceptions.ResourceNotFoundException;
import com.example.trendmart.repositories.ICategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor  
public class CategoryService implements ICategoryService {
  private final ICategoryRepository categoryRepository;

  @Override
  public Category getCategoryById(Long id) {
    return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
  }

  @Override
  public Category getCategoryByName(String name) {
    return categoryRepository.findByName(name);
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public Category addCategory(Category category) {
    return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName())).map(categoryRepository::save).orElseThrow(() -> new AlreadyExistException(category.getName() + " already exists"));
  }

  @Override
  public Category updateCategory(Category category, Long id) { 
    return Optional.ofNullable(getCategoryById(id)).map(existingCategory -> {
      existingCategory.setName(category.getName());
      return categoryRepository.save(existingCategory);
    }).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
  }

  @Override
  public void deleteCategoryById(Long id) {
    categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
      throw new ResourceNotFoundException("Category not found");
    });
  }
}
