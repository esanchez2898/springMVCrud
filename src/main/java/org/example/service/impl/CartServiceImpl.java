package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.CartDto;
import org.example.entity.CartEntity;
import org.example.exception.exceptions.InstanceNotFoundException;
import org.example.repository.CartRepository;
import org.example.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final TempConverter converter;

    public CartServiceImpl(CartRepository cartRepository, TempConverter converter) {
        this.cartRepository = cartRepository;
        this.converter = converter;
    }

    @Override
    public CartDto getCartById(Integer cartId) {
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);

        if (cartEntityOptional.isEmpty()) {
            throw new InstanceNotFoundException("Cart with id " + cartId + " was not found");
        }

        return converter.entityToDto(cartEntityOptional.get());
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



}
