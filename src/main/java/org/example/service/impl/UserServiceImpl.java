package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.ProductDto;
import org.example.dto.UserDto;
import org.example.entity.ProductEntity;
import org.example.entity.UserEntity;
import org.example.exception.exceptions.DuplicateFoundException;
import org.example.exception.exceptions.UserNotFoundException;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TempConverter converte;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TempConverter converte) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.converte = converte;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> returnValue = userRepository.findAll();
        List<UserDto> usersDtos = new ArrayList<>();

        for (UserEntity u : returnValue) {
            usersDtos.add(converte.entityToDto(u));
        }

        return usersDtos;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty()) {
            throw new UserNotFoundException("User was not found");
        }

        return converte.entityToDto(userEntityOptional.get());
    }

    @Override
    public UserDto addUser(UserDto userDto) {

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userDto.getEmail());

        if (userEntityOptional.isPresent()) {
            throw new DuplicateFoundException("User with email " + userDto.getEmail() + " already exist");
        }

        UserEntity userEntity = converte.dtoToEntity(userDto);
        UserEntity userEntitySaved =  userRepository.save(userEntity);

        return converte.entityToDto(userEntitySaved);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {

        //userRepository.findById(userDto.getRolesIds());

        UserDto currentUser = getUserById(userId);

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userDto.getEmail());

        if (userEntityOptional.isPresent()) {
            if (!Objects.equals(userEntityOptional.get().getId(), currentUser.getId())) {
                throw new DuplicateFoundException("User with email " + userDto.getEmail() + " already exist");
            }
        }

        userDto.setId(userId);
        UserEntity userEntity = converte.dtoToEntity(userDto);

        UserEntity userEntitySaved = userRepository.save(userEntity);

        return converte.entityToDto(userEntitySaved);

    }

    @Override
    public void deleteUserById(Integer userId) {
        getUserById(userId);
        userRepository.deleteById(userId);


    }
}
