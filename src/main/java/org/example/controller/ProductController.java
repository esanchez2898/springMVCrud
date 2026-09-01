package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ProductDto;
import org.example.exception.exceptions.DataNotValidatedException;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductService productService;
    //private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Integer productId) {
        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto, Errors errors) {

        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }
        ProductDto savedProduct =  productService.addProduct(productDto);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Integer productId, @RequestBody @Valid ProductDto productDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }

        ProductDto updatedProduct =  productService.updateProduct(productId, productDto);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer productId) {

        productService.deleteProductById(productId);

        return new ResponseEntity<>("Product with ID " + productId + " was successfully deleted.", HttpStatus.OK);

    }

}
