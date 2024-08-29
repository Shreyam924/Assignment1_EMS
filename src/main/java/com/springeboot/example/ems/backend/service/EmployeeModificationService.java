package com.springeboot.example.ems.backend.service;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.exception.ResourceNotFoundException;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data

public  class EmployeeModificationService  {

    private EmployeeRepository employeeRepository;

    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee= employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoundException("Employee does not exists "+employeeId)
        );
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj=employeeRepository.save(employee);
        //return EmployeeDto.mapToEmployeeDto(updatedEmployeeObj);
        return new EmployeeDto(updatedEmployeeObj);
    }
}
