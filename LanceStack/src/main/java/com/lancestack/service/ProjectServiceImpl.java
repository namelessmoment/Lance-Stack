package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.PostProjectDTO;
import com.lancestack.dto.ProjectDTO;
import com.lancestack.dto.ProjectFilterRangeDTO;
import com.lancestack.dto.UserDTO;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectStatus;
import com.lancestack.entities.User;
import com.lancestack.repository.ProjectRepository;
import com.lancestack.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepo;
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	// Need to look when integrating if we need user ID then provide it.
	@Override
	public ApiResponse postProject(PostProjectDTO projectDTO) {
//		User user = projectDTO.getUser();
//		User user = userRepo.findByEmail(projectDTO.getUser().getEmail());
//		projectDTO.setUser(user);
//		Project project = modelMapper.map(projectDTO, Project.class);
//		projectRepo.save(project);
//		return new ApiResponse("Project Successfully Added.");
		
//		UserDTO userDTO = projectDTO.getUser();
		User user = userRepo.findByEmail(projectDTO.getUser().getEmail());
		UserDTO userDTO1 = modelMapper.map(user, UserDTO.class);
	    projectDTO.setUser(userDTO1);
	    Project project = modelMapper.map(projectDTO, Project.class);
	    projectRepo.save(project);
	    return new ApiResponse("Project Successfully Added.");

	}

	@Override
	public List<ProjectDTO> getAllProjects() {
		List<Project> projects = projectRepo.findAll();
	    List<ProjectDTO> projectDTOs = projects.stream()
	            .map(project -> modelMapper.map(project, ProjectDTO.class))
	            .collect(Collectors.toList());
	    return projectDTOs;
	}

	@Override
	public Project getProjectById(Long id) {
		Project project = projectRepo.findById(id).orElseThrow(()-> new ResourceNotFound("Invalid Id"));
		return project;
	}

	@Override
	public ApiResponse updateProject(Long id, ProjectDTO proj) {
//		Optional<Project> existingProject = projectRepo.findById(id);
		String msg = "Updation Failed";
		Project existingProject = projectRepo.findById(id).orElse(null);
		if(existingProject==null) {
			throw  new ResourceNotFound("Invalid Id");
		}
		else {
			existingProject.setTitle(proj.getTitle());
			existingProject.setStatus(proj.getStatus());
			existingProject.setProjType(proj.getProjType());
			existingProject.setDescription(proj.getDescription());
			existingProject.setBudget(proj.getBudget());
			
//			User user = proj.getUser();
//			if(user != null && user.getId() != null) {
//				User existingUser = userRepo.findById(user.getId())
//						.orElseThrow(() -> new ResourceNotFound("User not found with Id:"+ user.getId()));
//				existingProject.setUser(existingUser);
//			}
//			else {
//				throw new ResourceNotFound("Invalid User");
//			}
			projectRepo.save(existingProject);
			msg = "Updatioon Successfull";
		}
		return new ApiResponse(msg);
	}
	
	@Override
	public List<ProjectDTO> getAllProjectsByUser(Long userId) {
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFound("User not found"));
	    List<Project> projects = user.getAllProjects();
	    return projects.stream()
	            .map(project -> modelMapper.map(project, ProjectDTO.class))
	            .collect(Collectors.toList());
	}


	@Override
	public ApiResponse updateStatus(Long projecId) {
			Project proj = projectRepo.findById(projecId).orElseThrow(()-> new ResourceNotFound("Invalid ProjectId"));
			proj.setStatus(ProjectStatus.COMPLETED);
			projectRepo.save(proj);
		return new ApiResponse("Project Status Updated Successfully");
	}

	@Override
	public List<Project> filterRange(ProjectFilterRangeDTO filterDTO) {
		
		return projectRepo.findProjectsByBudgetRange(filterDTO.getStartRange() , filterDTO.getEndRange());
	}	
}
