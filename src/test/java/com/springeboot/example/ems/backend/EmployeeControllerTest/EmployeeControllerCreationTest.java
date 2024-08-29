package com.springeboot.example.ems.backend.EmployeeControllerTest;

import com.springeboot.example.ems.backend.controller.EmployeeCreationController;
import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.service.EmployeeCreationService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerCreationTest {


    @InjectMocks
    private EmployeeCreationController employeeCreationController;

    @Mock
    private EmployeeCreationService employeeCreationServiceMock;

    @Test
    void testCreateEmployeeSuccess() throws BadRequestException {
        EmployeeDto validRequestDto = new EmployeeDto(1L, "xxxx", "Davis", "dsdgaex@gmail.com");

        when(employeeCreationServiceMock.createEmployee(validRequestDto)).thenReturn(validRequestDto);
        ResponseEntity<?> response  = employeeCreationController.createEmployee(validRequestDto);
        verify(employeeCreationServiceMock, times(1)).createEmployee(any(EmployeeDto.class));
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    void testCreateEmployeeBadRequest() throws BadRequestException {
        EmployeeDto invalidRequestDto = new EmployeeDto(1L, "xxxx", "Davis", "dsdgaexgmail.com");
        when(employeeCreationServiceMock.createEmployee(invalidRequestDto)).thenThrow(BadRequestException.class);

        // EmployeeDto savedEmployeeDto = null;
        ResponseEntity<?> response= employeeCreationController.createEmployee(invalidRequestDto);
        verify(employeeCreationServiceMock, times(1)).createEmployee(any(EmployeeDto.class));
        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
        //assertEquals(response,400);
        }

    @Test
    void testCreateEmployeeInternalServerError() throws BadRequestException {
        EmployeeDto employeeDto = new EmployeeDto(1L, "John", "Doe", "john.doe@example.com");

        // When
        when(employeeCreationServiceMock.createEmployee(employeeDto))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = employeeCreationController.createEmployee(employeeDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while creating the employee", response.getBody());

        verify(employeeCreationServiceMock, times(1)).createEmployee(employeeDto);
    }
    }



