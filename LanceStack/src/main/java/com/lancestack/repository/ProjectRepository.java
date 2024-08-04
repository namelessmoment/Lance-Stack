package com.lancestack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	
}
