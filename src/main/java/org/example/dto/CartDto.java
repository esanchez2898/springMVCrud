package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public class CartDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double price;

    @NotNull
    private List<Integer> cartItemsIds;

    @NotNull
    private Integer customerId;

    public CartDto() {
    }

    public CartDto(Integer id, Double price, List<Integer> cartItemsIds, Integer customerId) {
        this.id = id;
        this.price = price;
        this.cartItemsIds = cartItemsIds;
        this.customerId = customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Integer> getCartItemsIds() {
        return cartItemsIds;
    }

    public void setCartItemsIds(List<Integer> cartItemsIds) {
        this.cartItemsIds = cartItemsIds;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}