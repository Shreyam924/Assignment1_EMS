package com.springeboot.example.ems.backend.repository;

import com.springeboot.example.ems.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
