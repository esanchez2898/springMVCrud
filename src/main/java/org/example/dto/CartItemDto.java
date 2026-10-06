package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public class CartItemDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    @Positive
    private Integer quantity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double totalPrice;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer cartId;

    public CartItemDto() {
    }

    public CartItemDto(Integer id, Integer quantity, Double totalPrice, Integer productId, Integer cartId) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.productId = productId;
        this.cartId = cartId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
}
