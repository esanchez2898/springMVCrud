package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.converter.TempConverter;
import org.example.dto.CategoryDto;
import org.example.entity.CategoryEntity;
import org.example.exception.exceptions.CategoryNotFoundException;
import org.example.exception.exceptions.DuplicateFoundException;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final TempConverter tempConverter;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {

        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryDtoList.add(tempConverter.entityToDto(categoryEntity));
        }

        return categoryDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Integer categoryId) {

        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);

        if (categoryEntityOptional.isEmpty()) {
            throw new CategoryNotFoundException("Category was not found");
        }

        return tempConverter.entityToDto(categoryEntityOptional.get());
    }

    @Override
    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByName(categoryDto.getNameCategory());

        if (categoryEntityOptional.isPresent()) {
            throw new DuplicateFoundException("Category with name " + categoryDto.getNameCategory() + " already exist");
        }

        CategoryEntity categoryEntity = tempConverter.dtoToEntity(categoryDto);
        CategoryEntity categoryEntitySaved = categoryRepository.save(categoryEntity);

        return tempConverter.entityToDto(categoryEntitySaved);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {

        CategoryDto currentCategory = getCategoryById(categoryId);

        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findByName(categoryDto.getNameCategory());

        if (categoryEntityOptional.isPresent()) {
            if (!Objects.equals(categoryEntityOptional.get().getId(), currentCategory.getId())) {
                throw new DuplicateFoundException("Category with name " + categoryDto.getNameCategory() + " already exist");
            }
        }

        categoryDto.setId(categoryId);
        categoryDto.setProductsIds(currentCategory.getProductsIds());

        CategoryEntity categoryEntity = tempConverter.dtoToEntity(categoryDto);
        CategoryEntity categoryEntitySaved = categoryRepository.save(categoryEntity);

        return tempConverter.entityToDto(categoryEntitySaved);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Integer categoryId) {
        getCategoryById(categoryId);
        categoryRepository.deleteById(categoryId);
    }
}




