package com.exercise.demo.repository;

import com.exercise.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;


//Database operations for Department.
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
