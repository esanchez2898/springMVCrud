package org.example.service.impl;

import org.example.converter.TempConverter;
import org.example.dto.ProductDto;
import org.example.dto.UserDto;
import org.example.entity.ProductEntity;
import org.example.entity.RoleEntity;
import org.example.entity.UserEntity;
import org.example.exception.exceptions.DuplicateFoundException;
import org.example.exception.exceptions.InstanceNotFoundException;
import org.example.exception.exceptions.UserNotFoundException;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TempConverter converte, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.converte = converte;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> returnValue = new ArrayList<>();

        for (UserEntity user : users) {
            returnValue.add(converte.entityToDto(user));
        }

        return returnValue;
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
        userDto.setEnabled((byte) 1);

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity = converte.dtoToEntity(userDto);

        String roleName = "ROLE_USER";

        RoleEntity roleEntity = roleRepository.findByName(roleName).orElse(null);

        if (roleEntity == null ) {
            throw new InstanceNotFoundException("Role " + roleName + " was not found");
        }

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleEntity);

        userEntity.setRoles(roles);

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

    @Override
    public void deactivateUserById(Integer userId) {
        UserDto userDto = getUserById(userId);
        userDto.setEnabled((byte) 0);
        userRepository.save(converte.dtoToEntity(userDto));
    }

    @Override
    public void activateUserById(Integer userId) {
        UserDto userDto = getUserById(userId);
        userDto.setEnabled((byte) 1);
        userRepository.save(converte.dtoToEntity(userDto));
    }
}
