package com.springeboot.example.ems.backend.entity;

import com.springeboot.example.ems.backend.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;


    public User(UserDto userDto)
    {
        username=userDto.getUsername();
        password=userDto.getPassword();
        role=userDto.getRole();
    }

    public List<GrantedAuthority> getRoles() {
        return Arrays.stream(role.split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim())) // Prefix with "ROLE_"
                .collect(Collectors.toList());
    }

}
