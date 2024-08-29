package com.springeboot.example.ems.backend.controller;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.service.EmployeeModificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeModificationController {
    private final EmployeeModificationService employeeModificationService;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody EmployeeDto updatedEmployee){
        try {
            EmployeeDto employeeDto = employeeModificationService.updateEmployee(employeeId, updatedEmployee);
            if (employeeDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
            return ResponseEntity.ok(employeeDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Employee data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the employee");
        }
    }
}
