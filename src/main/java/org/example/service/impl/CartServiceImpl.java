package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.CartDto;
import org.example.entity.CartEntity;
import org.example.repository.CartRepository;
import org.example.service.CartService;
import org.modelmapper.ModelMapper;

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final TempConverter converter;

    public CartServiceImpl(CartRepository cartRepository, TempConverter converter) {
        this.cartRepository = cartRepository;
        this.converter = converter;
    }

    @Override
    public CartDto addCart(CartDto cart) {

        CartEntity cartEntity = converter.dtoToEntity(cart);
        CartEntity cartSaved = cartRepository.save(cartEntity);

        return converter.entityToDto(cartSaved);
    }

    @Override
    public CartDto clearCart() { // 2 possible ways



        return null;
    }

    @Override
    public void refreshCartState(Integer cartId) {

    }

    @Override
    public CartDto getCartById(Integer cartId) {
        return null;
    }

}
