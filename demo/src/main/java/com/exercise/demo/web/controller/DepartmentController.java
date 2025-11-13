package com.exercise.demo.web.controller;

import com.exercise.demo.entity.Address;
import com.exercise.demo.entity.Department;
import com.exercise.demo.repository.AddressRepository;
import com.exercise.demo.repository.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exercise.demo.web.dto.CreateDepartmentRequest;

import java.net.URI;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {


    private final DepartmentRepository departmentRepo;
    private final AddressRepository addressRepo;

    public DepartmentController(DepartmentRepository departmentRepo, AddressRepository addressRepo) {
        this.departmentRepo = departmentRepo;
        this.addressRepo = addressRepo;
    }


    //get all departments
    @GetMapping
    public List<Department> getAll() {
        return departmentRepo.findAll();
    }


    //get by id, one dep.
    @GetMapping("/{id}")
    public ResponseEntity<Department> getOne(@PathVariable Long id) {
        return departmentRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //post a department
    @PostMapping
    public ResponseEntity<Department> create(@RequestBody CreateDepartmentRequest  req) {

        //if the address id doesnt exist -> error
        Address address = addressRepo.findById(req.addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + req.addressId));


        //new dep. object
        Department dept = new Department();
        dept.setName(req.name);
        dept.setCreationDate(LocalDateTime.now());

        //links the dep. to address
        dept.setAddress(address);

        Department saved = departmentRepo.save(dept);
        return ResponseEntity.created(URI.create("/api/departments/" + saved.getId())).body(saved);
    }
}
