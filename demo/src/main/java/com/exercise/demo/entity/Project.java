package com.exercise.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private BigDecimal budget;


    //one project can be assigned to many employees, this relationship is handled through projectassignments.java entity
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectAssignments> assignments = new ArrayList<>();


    //constructors
    public Project(){}
    public Project(String name, LocalDate startDate, LocalDate endDate, BigDecimal budget) {

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;

    }

    //getters setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    //new
    public List<ProjectAssignments> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ProjectAssignments> assignments) {
        this.assignments = assignments;
    }



}
