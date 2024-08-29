package com.springeboot.example.ems.backend.EmployeeControllerTest;

import com.springeboot.example.ems.backend.controller.EmployeeDeletionController;
import com.springeboot.example.ems.backend.service.EmployeeDeletionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerDeletionTest {

    @InjectMocks
    private EmployeeDeletionController employeeDeletionController;

    @Mock
    private EmployeeDeletionService employeeDeletionServiceMock;

    @Test
    void testDeleteEmployeeSuccess(){
        Long employeeId=1L;

        doNothing().when(employeeDeletionServiceMock).deleteEmployee(employeeId);
        ResponseEntity<?> response = employeeDeletionController.deleteEmployee(employeeId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Employee deleted successfully",response.getBody());

        verify(employeeDeletionServiceMock,times(1)).deleteEmployee(employeeId);
    }

    @Test
    void testDeleteEmployeeNotFound() {
        Long employeeId = 1L;

        // When
        doThrow(new NoSuchElementException("Employee not found")).when(employeeDeletionServiceMock).deleteEmployee(employeeId);

        ResponseEntity<?> response = employeeDeletionController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee not found", response.getBody());

        verify(employeeDeletionServiceMock, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void testDeleteEmployeeInternalServerError() {
        Long employeeId = 1L;

        // When
        doThrow(new RuntimeException("Unexpected error")).when(employeeDeletionServiceMock).deleteEmployee(employeeId);

        ResponseEntity<?> response = employeeDeletionController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while deleting the employee", response.getBody());

        verify(employeeDeletionServiceMock, times(1)).deleteEmployee(employeeId);
    }


}
