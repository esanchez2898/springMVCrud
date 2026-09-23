package org.example.service;

import org.example.dto.CustomerDto;
import org.example.dto.UserDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();
    CustomerDto getCustomerById(Integer customerId);

    CustomerDto addCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(Integer customerId, CustomerDto customerDto);
    void deleteCustomerById(Integer customerId);
}
