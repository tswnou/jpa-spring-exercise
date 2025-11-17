package com.exercise.demo.web.controller;


import com.exercise.demo.entity.Project;
import com.exercise.demo.entity.ProjectAssignments;
import com.exercise.demo.repository.ProjectRepository;

import com.exercise.demo.service.ProjectService;
import com.exercise.demo.web.dto.CreateProjectRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {


    private final ProjectRepository projectRepo;
    private final ProjectService projectService;

    public ProjectController(ProjectRepository projectRepo, ProjectService projectService) {

        this.projectRepo = projectRepo;
        this.projectService = projectService;

    }

    @GetMapping
    public List<Project> all() {

        return projectRepo.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> one(@PathVariable Long id) {
        return projectRepo.findById(id)
                .map(project -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", project.getId());
                    response.put("name", project.getName());
                    response.put("startDate", project.getStartDate());
                    response.put("endDate", project.getEndDate());
                    response.put("budget", project.getBudget());

                    // EMPLOYEES LIST
                    List<Map<String, Object>> employees = project.getAssignments().stream()
                            .map(assign -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("id", assign.getEmployee().getId());
                                map.put("name", assign.getEmployee().getName());
                                map.put("age", assign.getEmployee().getAge());
                                map.put("role", assign.getEmployee().getRole());
                                return map;
                            })
                            .toList();

                    response.put("employees", employees);

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping
    public ResponseEntity<Project> create(@RequestBody CreateProjectRequest req) {

        Project p = new Project();
        p.setName(req.name);
        p.setStartDate(req.startDate);
        p.setEndDate(req.endDate);
        p.setBudget(req.budget);

        Project saved = projectRepo.save(p);
        return ResponseEntity.created(URI.create("/api/projects" + saved.getId())).body(saved);


    }

    @PostMapping("/{projectId}/assign/{employeeId}")
    public ResponseEntity<ProjectAssignments> assign (@PathVariable Long projectId, @PathVariable Long employeeId) {
        ProjectAssignments link = projectService.assignEmployeeToProject(projectId, employeeId );
        return ResponseEntity.ok(link);
    }


}
