package com.springeboot.example.ems.backend.service;

import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.exception.ResourceNotFoundException;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public  class EmployeeDeletionService  {

    private EmployeeRepository employeeRepository;

    public void deleteEmployee(Long employeeId) {

        Employee employee= employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoundException("Employee does not exists "+employeeId)
        );

        employeeRepository.deleteById(employeeId);

    }
}
