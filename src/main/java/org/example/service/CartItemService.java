package org.example.service;

import org.example.dto.CartItemDto;

import java.util.List;

public interface CartItemService {

    CartItemDto addItem(Integer productId);
    void removeItem(Integer itemId);
    List<CartItemDto> getAllByProductId(Integer productId);
    List<CartItemDto> getAllByCartId(Integer cartId);

}
