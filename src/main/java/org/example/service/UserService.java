package org.example.service;

import org.example.dto.AddressDto;
import org.example.dto.AuthenticationRequest;
import org.example.dto.UserDto;
import org.springframework.security.core.Authentication;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Integer userId);
    UserDto getUserByEmal(String userEmail);
    UserDto getCurrentUser();
    UserDto addUser(UserDto userDto);
    UserDto updateUser(Integer userId, UserDto userDto);
    void deleteUserById(Integer userId);
    void deactivateUserById(Integer userId);
    void activateUserById(Integer userId);

    Optional<Authentication> authentocationUser(AuthenticationRequest authenticationRequest);

}
