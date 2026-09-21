package org.example.service;

import org.example.dto.AddressDto;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<AddressDto> getAllAddresses();
    AddressDto getAddressById(Integer addressId);

    AddressDto addAddress(AddressDto addressDto);
    AddressDto updateAddress(Integer addressId, AddressDto addressDto);
    void deleteAddressById(Integer addressId);


}
