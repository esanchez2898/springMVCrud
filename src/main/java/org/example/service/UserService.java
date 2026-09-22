package org.example.service;

import org.example.dto.AddressDto;
import org.example.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Integer userId);

    UserDto addUser(UserDto userDto);
    UserDto updateUser(Integer userId, UserDto userDto);
    void deleteUserById(Integer userId);
}
