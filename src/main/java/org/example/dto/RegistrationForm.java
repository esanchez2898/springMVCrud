package org.example.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {

    @Valid
    private UserDto user;

    @Valid
    private CustomerDto customer;

    @Valid
    private AddressDto address;

}
