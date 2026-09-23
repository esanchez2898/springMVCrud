package org.example.repository;

import org.example.entity.AddressEntity;
import org.example.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<CustomerEntity> findAll();
    Optional<CustomerEntity> findById(Integer customerId);
    Optional<CustomerEntity> findByPhone(String customerPhone);
    CustomerEntity save(CustomerEntity customerEntity);
    void deleteById(Integer customerId);

}
