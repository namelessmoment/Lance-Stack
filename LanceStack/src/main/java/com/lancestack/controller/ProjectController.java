package com.lancestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.PostProjectDTO;
import com.lancestack.dto.ProjectDTO;
import com.lancestack.dto.ProjectFilterRangeDTO;
import com.lancestack.entities.Bid;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectType;
import com.lancestack.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	ProjectService projectService;
	
	@GetMapping
	public ResponseEntity<?> getAllProjects(){
		return ResponseEntity
				.ok(projectService.getAllProjects());
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable Long projectId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectById(projectId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PostMapping
	public ResponseEntity<?> postProject(@RequestBody PostProjectDTO projectDTO){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(projectService.postProject(projectDTO));
	}
	
//	@PostMapping
//	public ResponseEntity<?> createProject(@RequestBody Project project){
//		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project));
//	}
	
	@PutMapping("/{projectId}")
	public ResponseEntity<?> updateProject(@PathVariable Long projectId , @RequestBody ProjectDTO proj){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(projectId, proj));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Project>> getProjectsByUser(@PathVariable Long userId){
        List<Project> projs = projectService.getAllProjectsByUser(userId);
        return ResponseEntity.ok(projs);
	}
	
	@GetMapping("/projectStatus/{projectId}")
	public ResponseEntity<ApiResponse> updateProjectStatus(@PathVariable Long projectId){
		return ResponseEntity.status(HttpStatus.OK).body(projectService.updateStatus(projectId));
	}
	
	@PostMapping("/filterByRange")
	public ResponseEntity<List<Project>> getProjectsByRange(@RequestBody ProjectFilterRangeDTO filterDTO){
		List<Project> projects = projectService.filterRange(filterDTO);
		return ResponseEntity.ok(projects);
	}
	
	@GetMapping("/filterByProjectType/{projectType}")
	public ResponseEntity<List<Project>> getProjectsByProjectType(@PathVariable ProjectType projectType){
		List<Project> projects = projectService.filterProjectType(projectType);
		return ResponseEntity.ok(projects);
	}
	
	@GetMapping("/completedProjects")
	public ResponseEntity<List<Project>> getProjectsWhereStatusCompleted(){ 
		List<Project> projects = projectService.projectsWhereStatusCompleted();
		return ResponseEntity.ok(projects);
	}
	
	@GetMapping("/InprogressProjects")
	public ResponseEntity<List<Project>> getProjectsWhereStatusInprogress(){ 
		List<Project> projects = projectService.projectsWhereStatusInprogress();
		return ResponseEntity.ok(projects);
	}
	
	
}
