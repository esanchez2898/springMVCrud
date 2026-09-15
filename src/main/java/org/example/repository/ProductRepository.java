package org.example.repository;

import org.example.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Integer id);

    Optional<ProductEntity> findByName(String productName);

    List<ProductEntity> findAllByCategoryId(Integer categoryId);

    ProductEntity save(ProductEntity productEntity);

    void deleteById(Integer productId);
}
