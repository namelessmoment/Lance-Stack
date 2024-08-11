package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Project.PostProjectDTO;
import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.dto.Project.ProjectFilterRangeDTO;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectType;

public interface ProjectService {
	
	List<ProjectDTO> getAllProjects();
	
	ProjectDTO getProjectById(Long id);
	
	ApiResponse updateProject(Long id, ProjectDTO proj);

	ApiResponse postProject(PostProjectDTO projectDTO);
	
	List<ProjectDTO> getAllProjectsByUser(Long userId);

	ApiResponse updateStatusToCompleted(Long projecId);
	
	List<Project> filterRange(ProjectFilterRangeDTO filterDTO);
	
	List<Project> filterProjectType(ProjectType projectType);
	
	List<Project> projectsWhereStatusCompleted();
	
	List<Project> projectsWhereStatusInprogress();

	List<ProjectDTO> getProjectsByCompletedContracts();

	List<ProjectDTO> getCompletedProjectsByFreelancerId(Long freelancerId);

	List<ProjectDTO> getOnGoingProjectsByFreelancerId(Long freelancerId);

	String getProjectTitleByProjectId(Long projectId);
	
	String getProjectDescByProjectId(Long projectId);
	ApiResponse updateStatusToInProcess(Long projectId);
}
