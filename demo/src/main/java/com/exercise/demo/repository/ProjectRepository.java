package com.exercise.demo.repository;

import com.exercise.demo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;


//Manages Project entities.
public interface ProjectRepository extends JpaRepository<Project, Long>{
}
