package org.example.repository;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findById(Integer categoryId);

    Optional<CategoryEntity> findByName(String nameCategory);

    CategoryEntity save(CategoryEntity categoryEntity);

    void deleteById(Integer categoryId);
}
