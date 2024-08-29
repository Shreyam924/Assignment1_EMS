package com.springeboot.example.ems.backend.controller;

import com.springeboot.example.ems.backend.service.EmployeeDeletionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeDeletionController {
    private final EmployeeDeletionService employeeDeletionService;
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long employeeId){
        try {
            employeeDeletionService.deleteEmployee(employeeId);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
