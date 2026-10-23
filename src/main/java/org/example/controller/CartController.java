package org.example.controller;

import org.example.dto.CartDto;
import org.example.dto.CartItemDto;
import org.example.service.CartItemService;
import org.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    @Autowired
    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping(value = "/myCart")
    public ResponseEntity<CartDto> provideMyCart() {
        return new ResponseEntity<>(cartService.provideMyCart(), HttpStatus.OK);
    }

    @PostMapping(value = "/addItem/{productId}")
    public ResponseEntity<CartItemDto> addItem(@PathVariable("productId") Integer productId) {
        return new ResponseEntity<>(cartItemService.addItem(productId), HttpStatus.OK);
    }

}
