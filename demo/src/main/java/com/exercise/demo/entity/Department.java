package com.exercise.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity //jpa entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "address_id") //foreign key column in department table
    //Many departments can share the same address
    private Address address;

    //constructorss
    public Department(){}

    public Department(String name, LocalDateTime creationDate, Address address) {

        this.name = name;
        this.creationDate = creationDate;
        this.address = address;


    }


    //getters and setters

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }








}
