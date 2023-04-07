package com.samba.springbootsuperheroes.user.controller;


import com.samba.springbootsuperheroes.user.dto.UserDto;
import com.samba.springbootsuperheroes.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    public Iterable<UserDto> findAll() {
        return userService.getUsers();
    }

    @GetMapping("/api/v1/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) throws  NoSuchAlgorithmException {
        return userService.createUser(userDto, userDto.getPassword());
    }

    @PutMapping("/api/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @Valid @RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        userService.updateUser(id, userDto, userDto.getPassword());
    }
    @DeleteMapping("/api/v1/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        userService.removeUserById(id);
    }
}
