//Employee JPA entity
package com.springeboot.example.ems.backend.entity;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="employees")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable=false)
    private String firstName;

    @Column(name = "last_name",nullable=false)
    private String lastName;

    @Column(name = "email_id",nullable=false)
    private String email;

    public  Employee (EmployeeDto employeeDto){
        id=employeeDto.getId();
        firstName=employeeDto.getFirstName();
        lastName=  employeeDto.getLastName();
        email= employeeDto.getEmail();
    }
}
