package org.example.repository;

import org.example.entity.CartItemEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
    List<CartItemEntity> findAllByCartId(Integer cartId);
    CartItemEntity save(CartItemEntity itemEntity);
    Optional<CartItemEntity> findById(Integer itemId);
    void deleteById(Integer itemId);
}
