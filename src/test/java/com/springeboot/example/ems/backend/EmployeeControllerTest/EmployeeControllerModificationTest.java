package com.springeboot.example.ems.backend.EmployeeControllerTest;

import com.springeboot.example.ems.backend.controller.EmployeeModificationController;
import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.service.EmployeeModificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//Successful Update
public class EmployeeControllerModificationTest {


    @Mock
    private EmployeeModificationService employeeModificationServiceMock;

    @InjectMocks
    private EmployeeModificationController employeeModificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testUpdateEmployeeSuccess(){
        Long employeeId=1L;
        EmployeeDto updatedEmployee = new EmployeeDto(employeeId, "John", "Doe", "john.doe@example.com");
        EmployeeDto returnedEmployee = new EmployeeDto(employeeId, "John", "Doe", "john.doe@example.com");

        // When
        when(employeeModificationServiceMock.updateEmployee(employeeId, updatedEmployee)).thenReturn(returnedEmployee);

        ResponseEntity<?> response = employeeModificationController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnedEmployee, response.getBody());

        verify(employeeModificationServiceMock, times(1)).updateEmployee(employeeId, updatedEmployee);
    }

    // Employee Not Found
    @Test
    void testUpdateEmployeeNotFound() {

        Long employeeId = 1L;
        EmployeeDto updatedEmployee = new EmployeeDto(employeeId, "John", "Doe", "john.doe@example.com");

        // When
        when(employeeModificationServiceMock.updateEmployee(employeeId, updatedEmployee)).thenReturn(null);

        ResponseEntity<?> response = employeeModificationController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee not found", response.getBody());

        verify(employeeModificationServiceMock, times(1)).updateEmployee(employeeId, updatedEmployee);
    }

    //Invalid Employee Data
    @Test
    void testUpdateEmployeeBadRequest() {
        Long employeeId = 1L;
        EmployeeDto updatedEmployee = new EmployeeDto(employeeId, "John", "Doe", "john.doe@example.com");

        // When
        when(employeeModificationServiceMock.updateEmployee(employeeId, updatedEmployee))
                .thenThrow(new IllegalArgumentException("Invalid Employee data"));

        ResponseEntity<?> response = employeeModificationController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Employee data", response.getBody());

        verify(employeeModificationServiceMock, times(1)).updateEmployee(employeeId, updatedEmployee);
    }

    //Internal Server Error
    @Test
    void testUpdateEmployeeInternalServerError() {
        Long employeeId = 1L;
        EmployeeDto updatedEmployee = new EmployeeDto(employeeId, "John", "Doe", "john.doe@example.com");

        // When
        when(employeeModificationServiceMock.updateEmployee(employeeId, updatedEmployee))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = employeeModificationController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while updating the employee", response.getBody());

        verify(employeeModificationServiceMock, times(1)).updateEmployee(employeeId, updatedEmployee);
    }
}
