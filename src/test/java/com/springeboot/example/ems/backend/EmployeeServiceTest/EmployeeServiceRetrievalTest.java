package com.springeboot.example.ems.backend.service;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import com.springeboot.example.ems.backend.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceRetrievalTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeRetrievalService employeeRetrievalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeeById_Success() {
        // Arrange
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", "john.doe@example.com");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        EmployeeDto result = employeeRetrievalService.getEmployeeById(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());

        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeRetrievalService.getEmployeeById(employeeId);
        });

        assertEquals("Employee does not exist with the given id " + employeeId, exception.getMessage());

        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testGetAllEmployees_Success() {
        // Arrange
        Employee employee1 = new Employee(1L, "John", "Doe", "john.doe@example.com");
        Employee employee2 = new Employee(2L, "Jane", "Doe", "jane.doe@example.com");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        // Act
        List<EmployeeDto> result = employeeRetrievalService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmployees_NotFound() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<EmployeeDto> result = employeeRetrievalService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(employeeRepository, times(1)).findAll();
    }
}
