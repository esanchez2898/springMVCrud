package org.example.service;

import org.example.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategoryId(Integer categoryId);

    ProductDto getProductById(Integer id);

    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(Integer productId, ProductDto productDto);
    void deleteProductById(Integer productId);


}
