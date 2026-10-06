package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddressDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    private Integer customerId;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String country;

    @NotEmpty
    @Size(min = 5, max = 100)
    private String state;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String city;

    @NotEmpty
    @Size(min = 5, max = 300)
    private String street;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String postalCode;

    @Size(max = 300)
    private String deliveryInstructions;

    public AddressDto() {
    }

    public AddressDto(Integer id, Integer customerId, String country, String state, String city, String street, String postalCode, String deliveryInstructions) {
        this.id = id;
        this.customerId = customerId;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.deliveryInstructions = deliveryInstructions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }
}
