package com.springeboot.example.ems.backend.service;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.exception.ResourceNotFoundException;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Data
public  class EmployeeRetrievalService {

    private EmployeeRepository employeeRepository;

    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with the given id " + employeeId));

        //return EmployeeDto.mapToEmployeeDto(employee);
        return new EmployeeDto(employee);
    }


    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> new EmployeeDto(employee))
                .toList();
    }
    }
