package com.springeboot.example.ems.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.service.EmployeeModificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeModificationController {

    private final EmployeeModificationService employeeModificationService;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("id") Long employeeId,
            @RequestParam("employeeData") String employeeData,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmployeeDto updatedEmployee = objectMapper.readValue(employeeData, EmployeeDto.class);

            EmployeeDto employeeDto = employeeModificationService.updateEmployee(employeeId, updatedEmployee, image);
            if (employeeDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
            return ResponseEntity.ok(employeeDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating employee: " + e.getMessage());
        }
    }
}
