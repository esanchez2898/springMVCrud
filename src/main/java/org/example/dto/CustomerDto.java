package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CustomerDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    @Positive
    @Size(max = 12)
    private String customerPhone;

    @NotNull
    @Positive
    private Integer addressId;

    @NotNull
    @Positive
    private Integer userId;

    @NotNull
    @Positive
    private Integer cartId;

    public CustomerDto() {
    }

    public CustomerDto(Integer id, String customerPhone, Integer addressId, Integer userId, Integer cartId) {
        this.id = id;
        this.customerPhone = customerPhone;
        this.addressId = addressId;
        this.userId = userId;
        this.cartId = cartId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
}
