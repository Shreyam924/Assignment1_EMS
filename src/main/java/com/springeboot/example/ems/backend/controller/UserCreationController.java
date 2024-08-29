package com.springeboot.example.ems.backend.controller;

import com.springeboot.example.ems.backend.dto.UserDto;
import com.springeboot.example.ems.backend.entity.User;
import com.springeboot.example.ems.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserCreationController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User createUser(@RequestBody UserDto userData) {
        return userService.createUser(userData);
    }
}
