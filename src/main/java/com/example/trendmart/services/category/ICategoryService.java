package com.example.trendmart.services.category;

import java.util.List;

import com.example.trendmart.entities.Category;

public interface ICategoryService {
  Category getCategoryById(Long id);

  Category getCategoryByName(String name);

  List<Category> getAllCategories();

  Category addCategory(Category category);

  Category updateCategory(Category category, Long id);

  void deleteCategoryById(Long id);
}
