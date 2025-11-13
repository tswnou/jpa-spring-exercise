package com.exercise.demo.repository;

import com.exercise.demo.entity.EmployeePhone;
import org.springframework.data.jpa.repository.JpaRepository;


//CRUD operations for EmployeePhone

public interface EmployeePhoneRepository extends JpaRepository<EmployeePhone, Long> {
}
