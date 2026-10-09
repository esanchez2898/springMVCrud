package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.RoleDto;
import org.example.entity.RoleEntity;
import org.example.repository.RoleRepository;
import org.example.service.RoleService;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final TempConverter converter;

    public RoleServiceImpl(RoleRepository roleRepository, TempConverter converter) {
        this.roleRepository = roleRepository;
        this.converter = converter;
    }

    @Override
    public List<RoleDto> findAllByUserId(Integer userId) {
        List<RoleEntity> roleEntities = roleRepository.findAllByUserId(userId);
        List<RoleDto> returnValue = new ArrayList<>();

        for (RoleEntity role : roleEntities) {
            returnValue.add(converter.entityToDto(role));
        }
        return returnValue;
    }


}
