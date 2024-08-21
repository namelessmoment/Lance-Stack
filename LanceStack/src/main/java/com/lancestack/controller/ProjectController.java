package com.lancestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Project.PostProjectDTO;
import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.dto.Project.ProjectFilterRangeDTO;
import com.lancestack.dto.Project.ProjectTitleStatus;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectType;
import com.lancestack.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	ProjectService projectService;
	
	@Operation(description = "Get All projects.")
	@GetMapping
	public ResponseEntity<List<ProjectDTO>> getAllProjects(){
		return ResponseEntity
				.ok(projectService.getAllProjects());
	}
	
	@Operation(description = "Get Project By Project ID.")
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable Long projectId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectById(projectId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Post the Project")
	@PostMapping("/postProject")
	public ResponseEntity<?> postProject(@RequestBody PostProjectDTO projectDTO){
		try {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(projectService.postProject(projectDTO));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
//	@PostMapping
//	public ResponseEntity<?> createProject(@RequestBody Project project){
//		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project));
//	}
	
	@Operation(description = "Update Project details Endpoint.")
	@PutMapping("/{projectId}")
	public ResponseEntity<?> updateProject(@PathVariable Long projectId , @RequestBody ProjectDTO proj){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(projectId, proj));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	// All Projects by User ID
//	@GetMapping("/user/{userId}")
//	public ResponseEntity<List<Project>> getProjectsByUser(@PathVariable Long userId){
//        List<Project> projs = projectService.getAllProjectsByUser(userId);
//        return ResponseEntity.ok(projs);
//	}
	
//	@GetMapping("/userId/{userId}")
//	public ResponseEntity<List<ProjectDTO>> getProjectsByUser(@PathVariable Long userId){
//        List<ProjectDTO> projs = projectService.getAllProjectsByUser(userId);
//        return ResponseEntity.ok(projs);
//	}
	
	// Getting the project by userId
	@Operation(description = "Get Project by User ID.")
	@GetMapping("/user/{userId}/projects")
	public ResponseEntity<List<ProjectDTO>> getAllProjectsByUser(@PathVariable Long userId) {
	    List<ProjectDTO> projectDTOs = projectService.getAllProjectsByUser(userId);
	    return ResponseEntity.ok(projectDTOs);
	}
	
	@Operation(description = "Updating Project Status to Completed.")
	@GetMapping("/projectStatusToInProgress/{projectId}")
	public ResponseEntity<ApiResponse> updateStatusToInProcess(@PathVariable Long projectId){
		try {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.updateStatusToInProcess(projectId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}

	@Operation(description = "Updating Project Status to Completed.")
	@GetMapping("/projectStatusToCompleted/{projectId}")
	public ResponseEntity<ApiResponse> updateStatusToCompleted(@PathVariable Long projectId){
		try {
		return ResponseEntity.status(HttpStatus.OK).body(projectService.updateStatusToCompleted(projectId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Filter Project by the range on Budget.")
	@PostMapping("/filterByRange")
	public ResponseEntity<List<Project>> getProjectsByRange(@RequestBody ProjectFilterRangeDTO filterDTO){
		List<Project> projects = projectService.filterRange(filterDTO);
		return ResponseEntity.ok(projects);
	}
	
	@Operation(description = "Filter Project by Project Type.")
	@GetMapping("/filterByProjectType/{projectType}")
	public ResponseEntity<List<Project>> getProjectsByProjectType(@PathVariable ProjectType projectType){
		List<Project> projects = projectService.filterProjectType(projectType);
		return ResponseEntity.ok(projects);
	}
	
	@Operation(description = "Completed Project Endpoint.")
	@GetMapping("/completedProjects")
	public ResponseEntity<List<Project>> getProjectsWhereStatusCompleted(){ 
		List<Project> projects = projectService.projectsWhereStatusCompleted();
		return ResponseEntity.ok(projects);
	}
	
	@Operation(description = "In Progress Project Endpoint.")
	@GetMapping("/InprogressProjects")
	public ResponseEntity<List<Project>> getProjectsWhereStatusInprogress(){ 
		List<Project> projects = projectService.projectsWhereStatusInprogress();
		return ResponseEntity.ok(projects);
	}
	
	@Operation(description = "Fetch list of Projects which completed by specific freelancer using freelancer ID")
	@GetMapping("/completed/{freelancerId}")
	public ResponseEntity<List<ProjectDTO>> getCompletedProjectsByFreelancerId(@PathVariable Long freelancerId) {
	    List<ProjectDTO> projects = projectService.getCompletedProjectsByFreelancerId(freelancerId);
	    return ResponseEntity.ok(projects);
	}
	
	@Operation(description = "Fetch list of Projects which On Going/ in process by specific freelancer using freelancer ID")
	@GetMapping("/onGoing/{freelancerId}")
	public ResponseEntity<List<ProjectDTO>> getOnGoingProjectsByFreelancerId(@PathVariable Long freelancerId) {
	    List<ProjectDTO> projects = projectService.getOnGoingProjectsByFreelancerId(freelancerId);
	    return ResponseEntity.ok(projects);
	}
	
	@Operation(description = "To get Project Title from the ProjectID")
	@GetMapping("/projectTitle/{projectId}")
	public ResponseEntity<String> getProjectTitleByProjectId(@PathVariable Long projectId){
			return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectTitleByProjectId(projectId));
	}
	
	@Operation(description = "To get Project Desc from the ProjectID")
	@GetMapping("/projectDesc/{projectId}")
	public ResponseEntity<String> getProjectDescByProjectId(@PathVariable Long projectId){
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(projectService.getProjectDescByProjectId(projectId));
	}

	@Operation(description = "Get project title and status from project Id")
	@GetMapping("/projectTitleStatus/{projectId}")
	public ResponseEntity<ProjectTitleStatus> getProjectTitleStatus(@PathVariable Long projectId){
		return ResponseEntity.status(HttpStatus.OK)
				.body(projectService.getProjectTitleStatusByProjId(projectId));
	}
}
