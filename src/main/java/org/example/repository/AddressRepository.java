package org.example.repository;

import org.example.entity.AddressEntity;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    List<AddressEntity> findAll();
    Optional<AddressEntity> findById(Integer addressId);
    AddressEntity save(AddressEntity addressEntity);
    void deleteById(Integer addressId);

}
