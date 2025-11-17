package com.exercise.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    //Many employees can belong to the same department.
    private Department department;

    private String name;
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "address")
    //many employees may share the same address
    private Address address;

    private String role;

    //new
    //Deleting an employee automatically deletes their phones
    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL, orphanRemoval = true)
    //An employee can have multiple phone numbers.
    private List<EmployeePhone> phones = new ArrayList<>();


    //new
    @ManyToMany(mappedBy = "employees")
    @JsonManagedReference
    private List<Project> projects;



    //constructors

    public Employee() {

    }

    public Employee(String name, Integer age, String role, Department department, Address address) {

        this.name = name;
        this.age = age;
        this.role = role;
        this.department = department;
        this.address = address;

    }

    //getters setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    //new


    public List<EmployeePhone> getPhones() {
        return phones;
    }

    public void setPhones(List<EmployeePhone> phones) {
        this.phones = phones;
    }


}
