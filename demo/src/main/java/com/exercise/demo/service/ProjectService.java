package com.exercise.demo.service;

import com.exercise.demo.entity.Project;
import com.exercise.demo.entity.ProjectAssignments;
import com.exercise.demo.entity.Employee;
import com.exercise.demo.repository.ProjectRepository;
import com.exercise.demo.repository.EmployeeRepository;
import com.exercise.demo.repository.ProjectAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Marks this class as a Spring-managed service
@Service
public class ProjectService {

    private final ProjectRepository projectRepo;
    private final EmployeeRepository employeeRepo;
    private final ProjectAssignmentRepository assignementRepo;

    public ProjectService(ProjectRepository projectRepo, EmployeeRepository employeeRepo, ProjectAssignmentRepository assignementRepo) {

        this.projectRepo = projectRepo;
        this.employeeRepo = employeeRepo;
        this. assignementRepo = assignementRepo;
    }


    @Transactional
    public ProjectAssignments assignEmployeeToProject(Long projectId, Long employeeId) {

        //Load project validate exists
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project: " + projectId + " not found!"));
        //Load employee validate exists
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(() -> new IllegalArgumentException("Employee: " + employeeId + " not found!"));


        //relationship entry
        ProjectAssignments link = new ProjectAssignments(employee, project);

        //Adds the assignment to the project’s internal list
        project.getAssignments().add(link);


        return assignementRepo.save(link);





    }

}
