package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.CustomerDto;
import org.example.exception.exceptions.DataNotValidatedException;
import org.example.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Integer customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> addNewCustomer(@RequestBody @Valid CustomerDto customerDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }
        return new ResponseEntity<>(customerService.addCustomer(customerDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Integer customerId, @RequestBody @Valid CustomerDto customerDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerDto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id") Integer customerId) {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>("Customer with id " + customerId + " was successfully deleted", HttpStatus.OK);
    }

}
