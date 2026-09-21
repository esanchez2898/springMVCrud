package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.AddressDto;
import org.example.entity.AddressEntity;
import org.example.exception.exceptions.AddressNotFoundException;
import org.example.repository.AddressRepository;
import org.example.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    public AddressDto getAddressById(Integer addressId) {

        Optional<AddressEntity> returnValue = addressRepository.findById(addressId);

        if (returnValue.isEmpty()) {
            throw new AddressNotFoundException("Address was not found");
        }

        return converter.entityToDto(returnValue.get());
    }

    @Override
    public AddressDto addAddress(AddressDto addressDto) {

        AddressEntity addressEntity = converter.dtoToEntity(addressDto);
        AddressEntity addressSaved =  addressRepository.save(addressEntity);

        return converter.entityToDto(addressSaved);
    }

    @Override
    public AddressDto updateAddress(Integer addressId, AddressDto addressDto) {

        getAddressById(addressId);

        addressDto.setId(addressId);

        AddressEntity addressEntity = converter.dtoToEntity(addressDto);
        AddressEntity addressSaved = addressRepository.save(addressEntity);

        return converter.entityToDto(addressSaved);
    }

    @Override
    public void deleteAddressById(Integer addressId) {

        getAddressById(addressId);
        addressRepository.deleteById(addressId);

    }
}
