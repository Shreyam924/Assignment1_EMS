package com.springeboot.example.ems.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {

    private String username;
    private String password;
    private String role;

    // Getters and setters
}

