package com.springeboot.example.ems.backend;

import com.springeboot.example.ems.backend.service.EmployeeCreationService;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceCreationTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeCreationService employeeCreationService;

    private Employee employee;
    private Employee employee1;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {

        employee1 = new Employee(1L, "xxxx", "Davis", "dsdgaex@gmail.com");
        employeeDto = new EmployeeDto(1L, "xxxx", "Davis", "dsdgaex@gmail.com");

    }


    @Test
    void testCreateEmployeeSuccess() {
        // Employee employee = new Employee(1L, "yyyy", "Davis", "dsdgaex@gmail.com");
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee1);

        EmployeeDto savedEmployeeDto = null;
        try {
            savedEmployeeDto = employeeCreationService.createEmployee(employeeDto);
        } catch (BadRequestException e) {

        }

        // verify(employeeRepository, times(1)).save(any(Employee.class));
        assertNotNull(savedEmployeeDto);
        assertEquals(employeeDto.getFirstName(), savedEmployeeDto.getFirstName());

    }

    @Test
    void testCreateEmployeeBadRequest() {
        // Arrange
        EmployeeDto invalidRequestDto = new EmployeeDto(1L, "xxxx", "Davis", "dsdgaexgmail.com");

        // Act & Assert
        try {
            employeeCreationService.createEmployee(invalidRequestDto);
            Assertions.fail("Expected BadRequestException was not thrown");
        } catch (BadRequestException e) {
            // Verify that save is not called
            verify(employeeRepository, times(0)).save(any(Employee.class));

            // Assert the exception message
            Assertions.assertEquals("Invalid email", e.getMessage());
        }
    }

}