package com.springeboot.example.ems.backend.service;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data

public  class EmployeeCreationService  {

    private EmployeeRepository employeeRepository;

    public EmployeeDto createEmployee(EmployeeDto employeeDto) throws BadRequestException {
        Employee employee= new Employee(employeeDto);
        if(!employeeDto.getEmail().contains("@"))
        {
            throw new BadRequestException("Invalid email");
        }
        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeDto(savedEmployee);
    }
}

