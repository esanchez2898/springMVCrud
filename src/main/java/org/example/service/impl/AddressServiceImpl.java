package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.AddressDto;
import org.example.entity.AddressEntity;
import org.example.repository.AddressRepository;
import org.example.service.AddressService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;
    private final TempConverter converter;

    public AddressServiceImpl(AddressRepository addressRepository, TempConverter converter) {
        this.addressRepository = addressRepository;
        this.converter = converter;
    }


    @Override
    public List<AddressDto> getAllAddresses() {

        List<AddressEntity> returnValue = addressRepository.findAll();
        List<AddressDto> addressDtos = new ArrayList<>();

        for (AddressEntity address: returnValue) {
            addressDtos.add(converter.entityToDto(address));
        }

        return addressDtos;
    }

    @Override
    public Optional<AddressDto> getAddressById(Integer addressId) {

        //AddressEntity returnValue = addressRepository.findById(addressId);

        //AddressDto addressDto = converter.entityToDto();


        return Optional.empty();
    }

    @Override
    public AddressDto addAddress(AddressDto addressDto) {
        return null;
    }

    @Override
    public AddressDto updateAddress(Integer addressId, AddressDto addressDto) {
        return null;
    }

    @Override
    public void deleteAddressById(Integer addressId) {

    }
}
