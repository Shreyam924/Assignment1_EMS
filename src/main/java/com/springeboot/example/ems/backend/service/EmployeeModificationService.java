package com.springeboot.example.ems.backend.service;

import com.springeboot.example.ems.backend.dto.EmployeeDto;
import com.springeboot.example.ems.backend.entity.Employee;
import com.springeboot.example.ems.backend.exception.ResourceNotFoundException;
import com.springeboot.example.ems.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;


@Service
@AllArgsConstructor
public class EmployeeModificationService {

    private final EmployeeRepository employeeRepository;
    private final FileStorageService fileStorageService;

    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee, MultipartFile image) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (!optionalEmployee.isPresent()) {
            return null;
        }

        Employee employee = optionalEmployee.get();
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        // Save image if provided
        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.saveFile(image);
            employee.setImagePath(imagePath);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDto(savedEmployee);
    }
}
