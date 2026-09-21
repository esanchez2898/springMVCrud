package org.example.repository;

import org.example.entity.AddressEntity;
import org.example.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserEntity> findAll();
    Optional<UserEntity> findById(Integer userId);
    UserEntity save(UserEntity userEntity);
    void deleteById(Integer userId);
}
