package org.example.dto;

import jakarta.validation.Valid;

public class RegistrationForm {

    @Valid
    private UserDto user;

    @Valid
    private CustomerDto customer;

    @Valid
    private AddressDto address;

    public RegistrationForm() {
    }

    public RegistrationForm(UserDto user, CustomerDto customer, AddressDto address) {
        this.user = user;
        this.customer = customer;
        this.address = address;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
