package com.exercise.demo.repository;

import com.exercise.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



//Main repository for Employee
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
