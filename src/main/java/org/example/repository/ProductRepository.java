package org.example.repository;

import org.example.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<ProductEntity> findAllByCategoryId(Integer categoryId);
    Optional<ProductEntity> findByName(String productName);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Integer id);

    ProductEntity save(ProductEntity productEntity);

    void deleteById(Integer productId);
}
