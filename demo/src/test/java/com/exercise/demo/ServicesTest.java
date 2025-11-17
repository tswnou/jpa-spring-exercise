package com.exercise.demo;

import com.exercise.demo.entity.Employee;
import com.exercise.demo.entity.Project;
import com.exercise.demo.entity.ProjectAssignments;
import com.exercise.demo.repository.EmployeeRepository;
import com.exercise.demo.repository.ProjectAssignmentRepository;
import com.exercise.demo.repository.ProjectRepository;
import com.exercise.demo.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


//Enables Mockito in JUnit 5
@ExtendWith(MockitoExtension.class)
class ServicesTest {


    //Mock Repositories
    @Mock
    private ProjectRepository projectRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private ProjectAssignmentRepository assignmentRepo;

    //Inject ProjectService With Mocks, Automatically injects the mocked repos into your real service instance.
    @InjectMocks
    private ProjectService projectService;


    //service test
    @Test
    void testAssignEmployeeToProject() {

        // fake data

        // fake project
        Project project = new Project();
        project.setId(1L);
        project.setAssignments(new ArrayList<>());

        //fake employee
        Employee employee = new Employee();
        employee.setId(10L);

        // Mock repo behavior
        //"When service calls projectRepo.findById(1L), return this fake project"
        when(projectRepo.findById(1L)).thenReturn(Optional.of(project));
        //"When service calls employeeRepo.findById(10L), return this fake employee"
        when(employeeRepo.findById(10L)).thenReturn(Optional.of(employee));

        //Simulate Saving the Assignment
        ProjectAssignments savedAssignment = new ProjectAssignments(employee, project);
        when(assignmentRepo.save(any(ProjectAssignments.class))).thenReturn(savedAssignment);

        // Call service
        ProjectAssignments result = projectService.assignEmployeeToProject(1L, 10L);

        // Verify assignment added to project list
        assertEquals(1, project.getAssignments().size());
        assertSame(employee, result.getEmployee());
        assertSame(project, result.getProject());

        // Verify interactions
        //look up the project
        verify(projectRepo, times(1)).findById(1L);
        //look up the employee
        verify(employeeRepo, times(1)).findById(10L);
        //save the assignment
        verify(assignmentRepo, times(1)).save(any(ProjectAssignments.class));
    }
}
