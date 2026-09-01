package org.example.service;

import org.example.dto.CategoryDto;
import org.example.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Integer categoryId);
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto);
    void deleteCategoryById(Integer categoryId);

}
