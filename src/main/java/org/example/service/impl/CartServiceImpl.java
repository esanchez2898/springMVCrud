package org.example.service.impl;

import jakarta.inject.Provider;
import org.example.converter.TempConverter;
import org.example.dto.CartDto;
import org.example.dto.CustomerDto;
import org.example.dto.UserDto;
import org.example.entity.CartEntity;
import org.example.exception.exceptions.InstanceNotFoundException;
import org.example.repository.CartItemRepository;
import org.example.repository.CartRepository;
import org.example.service.CartService;
import org.example.service.CustomerService;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final Provider<CustomerService> customerServiceProvider;

    private final UserService userService;
    private final TempConverter converter;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, Provider<CustomerService> customerServiceProvider, UserService userService, TempConverter converter) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerServiceProvider = customerServiceProvider;
        this.userService = userService;
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
    public CartDto clearCart() {
        CustomerDto customerDto = customerServiceProvider.get().getCurrentCustomer();

        Integer cartId = customerDto.getCartId();

        cartItemRepository.deleteAllByCartId(cartId);
        refreshCartState(cartId);

        return getCartById(cartId);

    }

    @Override
    public void refreshCartState(Integer cartId) {

        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);

        if (cartEntityOptional.isEmpty()) {
            throw new InstanceNotFoundException("Cart wit id " + cartId + " was not found");
        }
        CartEntity cartEntity = cartEntityOptional.get();

        Optional<Double> totalCartOptional = cartRepository.calculateTotalPrice(cartId);

        if (totalCartOptional.isPresent()) {
            Double totalCart = totalCartOptional.get();
            cartEntity.setPrice(totalCart);
        } else {
            cartEntity.setPrice(0d);
        }
        cartRepository.save(cartEntity);

    }

    @Override
    public CartDto provideMyCart() {

        CustomerDto customer = customerServiceProvider.get().getCurrentCustomer();

        Integer cartId = customer.getCartId();

        return getCartById(cartId);
    }


}
