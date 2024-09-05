package com.springeboot.example.ems.backend.dto;

import com.springeboot.example.ems.backend.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String imagePath;

    public EmployeeDto(Employee employee) {
        id=employee.getId();
        firstName = employee.getFirstName();
        lastName = employee.getLastName();
        email = employee.getEmail();
        imagePath=employee.getImagePath();
    }
}
