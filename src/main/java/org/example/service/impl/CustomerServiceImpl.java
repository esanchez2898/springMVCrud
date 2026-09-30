package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.*;
import org.example.entity.CustomerEntity;
import org.example.entity.UserEntity;
import org.example.exception.exceptions.CustomerNotFoundException;
import org.example.exception.exceptions.DuplicateFoundException;
import org.example.repository.CustomerRepository;
import org.example.service.AddressService;
import org.example.service.CustomerService;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TempConverter converter;
    private final UserService userService;
    private final AddressService addressService;

    public CustomerServiceImpl(CustomerRepository customerRepository, TempConverter converter, UserService userService, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.converter = converter;
        this.userService = userService;
        this.addressService = addressService;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {

        List<CustomerEntity> returnValue = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();

        for (CustomerEntity c : returnValue) {
            customerDtos.add(converter.entityToDto(c));
        }

        return customerDtos;
    }

    @Override
    public CustomerDto getCustomerById(Integer customerId) {

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);

        if (customerEntityOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer was not found");
        }

        return converter.entityToDto(customerEntityOptional.get());
    }

    @Override
    public CustomerDto addCustomer(RegistrationForm form) {

        UserDto user = form.getUser();
        AddressDto address = form.getAddress();
        CustomerDto customerDto = form.getCustomer();

        CartDto cart = new CartDto();
        cart.setPrice(0d);

        UserDto storedUser  = userService.addUser(user);
        AddressDto storedAddress  = addressService.addAddress(address);

        //getCustomerById(customerDto.getId());

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByPhone(customerDto.getCustomerPhone());

        if (customerEntityOptional.isPresent()) {
            throw new DuplicateFoundException("Customer with phone " + customerDto.getCustomerPhone() + " already exist");
        }

        customerDto.setUserId(user.getId());
        customerDto.setAddressId(address.getId());
        customerDto.setCartId(cart.getId());

        CustomerEntity customerEntity = converter.dtoToEntity(customerDto);
        CustomerEntity customerEntitySaved = customerRepository.save(customerEntity);

        return converter.entityToDto(customerEntitySaved);
    }

    @Override
    public CustomerDto updateCustomer(Integer customerId, CustomerDto customerDto) {

        CustomerDto currentCustomer = getCustomerById(customerId);

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByPhone(customerDto.getCustomerPhone());

        if (customerEntityOptional.isPresent()) {
            if (!Objects.equals(customerEntityOptional.get().getId(), currentCustomer.getId())) {
                throw new DuplicateFoundException("Customer with phone " + customerDto.getCustomerPhone() + " already exist");
            }
        }

        customerDto.setId(customerId);

        CustomerEntity customerEntity = converter.dtoToEntity(customerDto);
        CustomerEntity customerEntitySaved = customerRepository.save(customerEntity);

        return converter.entityToDto(customerEntitySaved);
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        getCustomerById(customerId);
        customerRepository.deleteById(customerId);

    }
}
