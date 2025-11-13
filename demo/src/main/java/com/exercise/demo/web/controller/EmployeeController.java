package com.exercise.demo.web.controller;


import com.exercise.demo.entity.Address;
import com.exercise.demo.entity.Department;
import com.exercise.demo.entity.Employee;
import com.exercise.demo.repository.AddressRepository;
import com.exercise.demo.repository.DepartmentRepository;
import com.exercise.demo.repository.EmployeeRepository;
import com.exercise.demo.web.dto.CreateEmployeeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;
    private final AddressRepository addressRepo;


    public EmployeeController(EmployeeRepository employeeRepo, DepartmentRepository departmentRepo, AddressRepository addressRepo) {

        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.addressRepo = addressRepo;

    }


    //get all employees
    @GetMapping
    public List<Employee> all() {
        return employeeRepo.findAll();
    }


    //find one employee be id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> one(@PathVariable Long id) {
        return employeeRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    //post aan employee
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody CreateEmployeeRequest req) {

        //new employee obj
        Employee e = new Employee();
        e.setName(req.name);
        e.setAge(req.age);
        e.setRole(req.role);


        //if request contains dep id
        if (req.departmentId != null) {
            Department dept = departmentRepo.findById(req.departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Department not found: " + req.departmentId));
            e.setDepartment(dept);
        }


        //if request contains address id
        if (req.addressId != null) {
            Address addr = addressRepo.findById(req.addressId)
                    .orElseThrow(() -> new IllegalArgumentException("Address not found: " + req.addressId));
            e.setAddress(addr);
        }


        //employee saved
        Employee saved = employeeRepo.save(e);
        return ResponseEntity.created(URI.create("/api/employees/" + saved.getId())).body(saved);
    }


    //delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!employeeRepo.existsById(id)) return ResponseEntity.notFound().build();
        employeeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
