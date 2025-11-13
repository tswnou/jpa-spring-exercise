package com.exercise.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;




@Entity
@Table(name = "project_assignments")
public class ProjectAssignments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Many assignments can refer to the same employee
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    //Many assignments can refer to the same project
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    //constructors
    public ProjectAssignments() {}

    public ProjectAssignments(Employee employee, Project project) {
        this.employee = employee;
        this.project = project;
    }

    //getters setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;

    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


}
