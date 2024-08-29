package com.springeboot.example.ems.backend.controller;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.service.EmployeeCreationService;
import jakarta.servlet.http.HttpServlet;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")

public class EmployeeCreationController {
    private final EmployeeCreationService employeeCreationService;
    @PostMapping("/create")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            EmployeeDto savedEmp = employeeCreationService.createEmployee(employeeDto);
            return new ResponseEntity<>(savedEmp, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the employee");
        }
    }
}
