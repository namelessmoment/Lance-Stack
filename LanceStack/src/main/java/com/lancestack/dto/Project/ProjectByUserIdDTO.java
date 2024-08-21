package com.lancestack.dto.Project;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectByUserIdDTO{
	private String userName;
	private List<ProjectDTO> projects = new ArrayList<>();
	private String email;
	private String password;
	private String mobileNumber;
	
	//helper method to get all projects by user
	public List<ProjectDTO> getAllProjects() {
        return this.projects;
    }
	
}
