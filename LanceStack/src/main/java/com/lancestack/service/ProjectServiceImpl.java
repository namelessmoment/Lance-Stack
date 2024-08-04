package com.lancestack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.entities.Project;
import com.lancestack.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Override
	public ApiResponse createProject(Project proj) {
		projectRepo.save(proj);
		return new ApiResponse("Project created successfully");
	}

	@Override
	public List<Project> getAllProjects() {
		return projectRepo.findAll();
	}

	@Override
	public Project getProjectById(Long id) {
		Project project = projectRepo.findById(id).orElseThrow(()-> new ResourceNotFound("Invalid Id"));
		return project;
	}

	@Override
	public ApiResponse updateProject(Long id, Project proj) {
//		Optional<Project> existingProject = projectRepo.findById(id);
		String msg = "Updation Failed";
		Project existingProject = projectRepo.findById(id).orElse(null);
		if(existingProject==null) {
			throw  new ResourceNotFound("Invalid Id");
		}
		else {
			existingProject.setTitle(proj.getTitle());
			existingProject.setUser(proj.getUser());
			existingProject.setStatus(proj.getStatus());
			existingProject.setProjType(proj.getProjType());
			existingProject.setDescription(proj.getDescription());
			existingProject.setBudget(proj.getBudget());
			msg = "Updatioon Successfull";
		}
		return new ApiResponse(msg);
	}

}
