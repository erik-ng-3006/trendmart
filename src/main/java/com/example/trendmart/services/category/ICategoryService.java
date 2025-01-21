package com.example.trendmart.services.category;

import java.util.List;
import java.util.UUID;

import com.example.trendmart.entities.Category;

public interface ICategoryService {
  Category getCategoryById(UUID id);

  Category getCategoryByName(String name);

  List<Category> getAllCategories();

  Category addCategory(Category category);

  Category updateCategory(Category category, UUID id);

  void deleteCategoryById(UUID id);
}
