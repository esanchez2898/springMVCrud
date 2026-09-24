package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.exception.exceptions.DataNotValidatedException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

}

