package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.CartItemDto;
import org.example.dto.CustomerDto;
import org.example.entity.CartItemEntity;
import org.example.repository.CartItemRepository;
import org.example.service.CartItemService;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CustomerService customerService;
    private final TempConverter converter;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, CustomerService customerService, TempConverter converter) {
        this.cartItemRepository = cartItemRepository;
        this.customerService = customerService;
        this.converter = converter;
    }

    @Override
    public CartItemDto addItem(Integer productId) {

        int n = 1;
        CustomerDto customer = customerService.getCurrentCustomer();
        Integer cartId = customer.getCartId();

        CartItemDto cartItemDto = new CartItemDto();

        cartItemDto.setCartId(cartId);
        cartItemDto.setProductId(productId);
        cartItemDto.setQuantity(n);

        CartItemEntity cartItemSaved = cartItemRepository.save(converter.dtoToEntity(cartItemDto));
        return converter.entityToDto(cartItemSaved);
    }

    @Override
    public void removeItem(Integer itemId) {

    }

    @Override
    public List<CartItemDto> getAllByProductId(Integer productId) {
        return List.of();
    }

    @Override
    public List<CartItemDto> getAllByCartId(Integer cartId) {
        return List.of();
    }
}
