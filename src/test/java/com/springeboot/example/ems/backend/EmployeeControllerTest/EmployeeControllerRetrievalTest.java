package com.springeboot.example.ems.backend.EmployeeControllerTest;

import com.springeboot.example.ems.backend.controller.EmployeeRetrievalController;
import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.service.EmployeeRetrievalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmployeeControllerRetrievalTest {

    @InjectMocks
    private EmployeeRetrievalController employeeRetrievalController;

    @Mock
    private EmployeeRetrievalService employeeRetrievalServiceMock;

    @Test
    void testGetEmployeeByIdSuccess(){
        Long EmpId=1L;
        EmployeeDto employeeDto = new EmployeeDto(EmpId,"John", "Doe", "john.doe@example.com");

        //when
        when(employeeRetrievalServiceMock.getEmployeeById(EmpId)).thenReturn(employeeDto);

        ResponseEntity<?> response=employeeRetrievalController.getEmployeeById(EmpId);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employeeDto,response.getBody());

        verify(employeeRetrievalServiceMock,times(1)).getEmployeeById(EmpId);
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        Long EmpId = 1L;

        // When
        when(employeeRetrievalServiceMock.getEmployeeById(EmpId)).thenReturn(null);

        ResponseEntity<?> response = employeeRetrievalController.getEmployeeById(EmpId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Employee not found", response.getBody());

        verify(employeeRetrievalServiceMock, times(1)).getEmployeeById(EmpId);
    }

    @Test
    void testGetAllEmployeesSuccess() {
        List<EmployeeDto> employees = Arrays.asList(
                new EmployeeDto(1L,"John", "Doe", "john.doe@example.com"),
                new EmployeeDto(2L,"Jane", "Smith", "jane.smith@example.com")
        );

        // When
        when(employeeRetrievalServiceMock.getAllEmployees()).thenReturn(employees);

        ResponseEntity<?> response = employeeRetrievalController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employees, response.getBody());

        verify(employeeRetrievalServiceMock, times(1)).getAllEmployees();
    }

    @Test
    void testGetAllEmployeesNotFound() {
        List<EmployeeDto> emptyList = Collections.emptyList();

        // When
        when(employeeRetrievalServiceMock.getAllEmployees()).thenReturn(emptyList);

        ResponseEntity<?> response = employeeRetrievalController.getAllEmployees();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        //assertEquals("Employee Not found", response.getBody());

        verify(employeeRetrievalServiceMock, times(1)).getAllEmployees();
    }

}
