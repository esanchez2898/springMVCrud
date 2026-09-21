package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.AddressDto;
import org.example.exception.exceptions.DataNotValidatedException;
import org.example.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddress() {
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable("id") Integer addressId) {
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDto> addAddress(@RequestBody @Valid AddressDto addressDto, Errors errors) {

        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }

        return new ResponseEntity<>(addressService.addAddress(addressDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable("id") Integer addressId, @RequestBody @Valid AddressDto addressDto, Errors errors) {

        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }

        return new ResponseEntity<>(addressService.updateAddress(addressId, addressDto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable("id") Integer addressId) {

        addressService.deleteAddressById(addressId);
        return new ResponseEntity<>("Address with ID " + addressId + " was deleted successfully.", HttpStatus.OK);
    }



}
