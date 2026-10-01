package org.example.service;

import org.example.dto.CartDto;

public interface CartService {

    CartDto addCart(CartDto cart);

    CartDto clearCart();

    void refreshCartState(Integer cartId);

    CartDto getCartById(Integer cartId);

}
