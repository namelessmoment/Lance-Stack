package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.entities.Project;

public interface ProjectService {
	ApiResponse createProject(Project proj);
	
	List<Project> getAllProjects();
	
	Project getProjectById(Long id);
	
	ApiResponse updateProject(Long id, Project proj);
}
