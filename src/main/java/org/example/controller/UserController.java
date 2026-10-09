package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.AuthenticationRequest;
import org.example.dto.RoleDto;
import org.example.dto.UserDto;
import org.example.exception.exceptions.DataNotValidatedException;
import org.example.repository.UserRepository;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, RoleService roleService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> addNewUser(@RequestBody @Valid UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Integer userId, @RequestBody @Valid UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>("User with id " + userId + " was successfully deleted", HttpStatus.OK);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody @Valid AuthenticationRequest request, Errors errors) throws CredentialNotFoundException {
        if (errors.hasErrors()) {
            throw new DataNotValidatedException(errors.getFieldErrors());
        }

        Optional<Authentication> authenticationOptional = userService.authentocationUser(request);

        if (authenticationOptional.isEmpty()) {
            throw new CredentialNotFoundException("Invalid username or password");
        }

        UserDto userDto = userService.getUserByEmal(request.getUsername());
        List<RoleDto> roles = roleService.findAllByUserId(userDto.getId());
        List<String> roleNames = new ArrayList<>();

        for (RoleDto role : roles) {
            roleNames.add(role.getRoleName());
        }

        String jwtToken = jwtUtil.generateToken(userDto.getEmail(), roleNames);
        userDto.setAuthToken(jwtToken);

        // user should return and have a token to pass!!

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}

