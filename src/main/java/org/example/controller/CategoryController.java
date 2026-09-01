package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.CategoryDto;
import org.example.exception.exceptions.CategoryNotFoundException;
import org.example.exception.exceptions.DataNotValidatedException;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Integer categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto, Errors errors) {

        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Integer categoryId, @RequestBody @Valid CategoryDto categoryDto, Errors errors) {

        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }

        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer categoryId) {

        categoryService.deleteCategoryById(categoryId);

        return new ResponseEntity<>("Category with ID " + categoryId + " was deleted successfully.", HttpStatus.OK);


    }






}
