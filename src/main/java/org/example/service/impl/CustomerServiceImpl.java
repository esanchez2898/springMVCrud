package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.CustomerDto;
import org.example.dto.UserDto;
import org.example.entity.CustomerEntity;
import org.example.entity.UserEntity;
import org.example.exception.exceptions.CustomerNotFoundException;
import org.example.exception.exceptions.DuplicateFoundException;
import org.example.repository.CustomerRepository;
import org.example.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TempConverter converter;

    public CustomerServiceImpl(CustomerRepository customerRepository, TempConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
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
    public CustomerDto addCustomer(CustomerDto customerDto) {

        //getCustomerById(customerDto.getId());

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByPhone(customerDto.getCustomerPhone());

        if (customerEntityOptional.isPresent()) {
            throw new DuplicateFoundException("Customer with phone " + customerDto.getCustomerPhone() + " already exist");
        }

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
