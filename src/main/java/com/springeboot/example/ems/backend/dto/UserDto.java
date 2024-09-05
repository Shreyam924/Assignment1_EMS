package com.springeboot.example.ems.backend.dto;
import com.springeboot.example.ems.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String username;
    private String password;
    private String role;
    private String imagePath;

    public UserDto(User user) {
        username = user.getUsername();
        password = user.getPassword();
        role = user.getRole();
    }
}
