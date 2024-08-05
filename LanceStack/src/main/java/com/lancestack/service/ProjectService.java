package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.PostProjectDTO;
import com.lancestack.dto.ProjectDTO;
import com.lancestack.dto.ProjectFilterRangeDTO;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectStatus;

public interface ProjectService {
//	ApiResponse createProject(Project proj);
	
	List<ProjectDTO> getAllProjects();
	
	Project getProjectById(Long id);
	
	ApiResponse updateProject(Long id, ProjectDTO proj);

	ApiResponse postProject(PostProjectDTO projectDTO);
	
//	List<Project> getAllProjectsByUser(Long userId);
	List<ProjectDTO> getAllProjectsByUser(Long userId);

	ApiResponse updateStatus(Long projecId);
	
	List<Project> filterRange(ProjectFilterRangeDTO filterDTO);

}
