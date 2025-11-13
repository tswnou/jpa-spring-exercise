package com.exercise.demo.service;

import com.exercise.demo.entity.Project;
import com.exercise.demo.entity.ProjectAssignments;
import com.exercise.demo.entity.Employee;
import com.exercise.demo.entity.EmployeePhone;
import com.exercise.demo.entity.Address;
import com.exercise.demo.entity.Country;
import com.exercise.demo.entity.Department;
import com.exercise.demo.repository.ProjectRepository;
import com.exercise.demo.repository.EmployeeRepository;
import com.exercise.demo.repository.ProjectAssignementRepository;
import com.exercise.demo.repository.EmployeePhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Marks this class as a Spring-managed service
@Service
public class ProjectSevice {

    private final ProjectRepository projectRepo;
    private final EmployeeRepository employeeRepo;
    private final ProjectAssignementRepository assignementRepo;

    public ProjectSevice (ProjectRepository projectRepo, EmployeeRepository employeeRepo, ProjectAssignementRepository assignementRepo) {

        this.projectRepo = projectRepo;
        this.employeeRepo = employeeRepo;
        this. assignementRepo = assignementRepo;
    }


    //makes sure the entire method succeeds or fails as one unit
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
