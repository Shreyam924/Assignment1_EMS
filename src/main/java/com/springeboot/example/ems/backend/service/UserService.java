package com.springeboot.example.ems.backend.service;

import com.springeboot.example.ems.backend.dto.UserDto;
import com.springeboot.example.ems.backend.entity.User;
import com.springeboot.example.ems.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto);
        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setPassword(encodedPassword);
        System.out.println(userDto.toString());
        return userRepository.save(user);
    }


    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
