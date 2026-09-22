package org.example.repository;

import org.example.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<RoleEntity> findAll();
    Optional<RoleEntity> findById(Integer roleId);
    Optional<RoleEntity> findByName(Integer roleName);
    RoleEntity save(RoleEntity roleEntity);
    void deleteById(Integer roleId);
}
