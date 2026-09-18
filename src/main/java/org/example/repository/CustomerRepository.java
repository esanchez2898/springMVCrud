package org.example.repository;

import org.example.entity.CustomerEntity;

import java.util.Optional;

public interface CustomerRepository {
    Optional<CustomerEntity> findById(Integer customerId);
}
