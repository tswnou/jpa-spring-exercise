package com.exercise.demo.web.controller;

import com.exercise.demo.entity.Employee;
import com.exercise.demo.entity.EmployeePhone;
import com.exercise.demo.repository.EmployeePhoneRepository;
import com.exercise.demo.repository.EmployeeRepository;
import com.exercise.demo.web.dto.CreateEmployeePhoneRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    private final EmployeePhoneRepository phoneRepo;
    private final EmployeeRepository employeeRepo;

    public PhoneController(EmployeePhoneRepository phoneRepo, EmployeeRepository employeeRepo) {
        this.phoneRepo = phoneRepo;
        this.employeeRepo = employeeRepo;
    }

    // GET all phones
    @GetMapping
    public List<EmployeePhone> all() {
        return phoneRepo.findAll();
    }

    // GET phone by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeePhone> getOne(@PathVariable Long id) {
        return phoneRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: create phone
    @PostMapping
    public ResponseEntity<EmployeePhone> create(@RequestBody CreateEmployeePhoneRequest req) {

        Employee employee = employeeRepo.findById(req.employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + req.employeeId));

        EmployeePhone phone = new EmployeePhone();
        phone.setValue(req.value);
        phone.setEmployee(employee);

        EmployeePhone saved = phoneRepo.save(phone);

        return ResponseEntity.created(URI.create("/api/phones/" + saved.getId()))
                .body(saved);
    }

    // DELETE phone
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!phoneRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        phoneRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

