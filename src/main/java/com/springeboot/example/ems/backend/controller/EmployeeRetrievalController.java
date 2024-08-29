package com.springeboot.example.ems.backend.controller;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.service.EmployeeRetrievalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeRetrievalController {

    private final EmployeeRetrievalService employeeRetrievalService;

    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long employeeId){
        try {
            EmployeeDto employeeDto = employeeRetrievalService.getEmployeeById(employeeId);
            if (employeeDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
            return ResponseEntity.ok(employeeDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the employee");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        try {
            List<EmployeeDto> employees = employeeRetrievalService.getAllEmployees();
            if (employees.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the employees");
        }
    }


}
