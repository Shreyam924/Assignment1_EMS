package com.springeboot.example.ems.backend.EmployeeServiceTest;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.exception.ResourceNotFoundException;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import com.springeboot.example.ems.backend.service.EmployeeModificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class EmployeeServiceModificationTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeModificationService employeeModificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateEmployee_Success() {
        // Arrange
        Long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "John", "Doe", "john.doe@example.com");
        EmployeeDto updatedEmployeeDto = new EmployeeDto(employeeId, "Jane", "Doe", "jane.doe@example.com");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        EmployeeDto result = employeeModificationService.updateEmployee(employeeId, updatedEmployeeDto);

        // Assert
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("jane.doe@example.com", result.getEmail());

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void testUpdateEmployee_NotFound() {
        // Arrange
        Long employeeId = 1L;
        EmployeeDto updatedEmployeeDto = new EmployeeDto(employeeId, "Jane", "Doe", "jane.doe@example.com");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
         ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeModificationService.updateEmployee(employeeId, updatedEmployeeDto);
        });

        assertEquals("Employee does not exists " + employeeId, exception.getMessage());

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}
