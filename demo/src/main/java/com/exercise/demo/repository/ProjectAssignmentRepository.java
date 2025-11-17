package com.exercise.demo.repository;

import com.exercise.demo.entity.ProjectAssignments;
import org.springframework.data.jpa.repository.JpaRepository;


//Handles the assignment join table
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignments, Long> {
}
