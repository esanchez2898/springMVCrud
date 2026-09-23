package org.example.repository;

import org.example.entity.AddressEntity;
import org.example.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
    List<CartEntity> findAll();
    Optional<CartEntity> findById(Integer cartId);
    CartEntity save(CartEntity cartEntity);
    void deleteById(Integer cartId);
}
