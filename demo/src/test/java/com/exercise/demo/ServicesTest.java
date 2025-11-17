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

@ExtendWith(MockitoExtension.class)
class ServicesTest {

    @Mock
    private ProjectRepository projectRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private ProjectAssignmentRepository assignmentRepo;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void testAssignEmployeeToProject() {
        // Fake project
        Project project = new Project();
        project.setId(1L);
        project.setAssignments(new ArrayList<>());

        // Fake employee
        Employee employee = new Employee();
        employee.setId(10L);

        // Mock repo behavior
        when(projectRepo.findById(1L)).thenReturn(Optional.of(project));
        when(employeeRepo.findById(10L)).thenReturn(Optional.of(employee));

        ProjectAssignments savedAssignment = new ProjectAssignments(employee, project);
        when(assignmentRepo.save(any(ProjectAssignments.class))).thenReturn(savedAssignment);

        // Call service
        ProjectAssignments result = projectService.assignEmployeeToProject(1L, 10L);

        // Verify assignment added to project list
        assertEquals(1, project.getAssignments().size());
        assertSame(employee, result.getEmployee());
        assertSame(project, result.getProject());

        // Verify interactions
        verify(projectRepo, times(1)).findById(1L);
        verify(employeeRepo, times(1)).findById(10L);
        verify(assignmentRepo, times(1)).save(any(ProjectAssignments.class));
    }
}
