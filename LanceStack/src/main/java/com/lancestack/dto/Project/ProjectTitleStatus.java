package com.lancestack.dto.Project;

import com.lancestack.entities.ProjectStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectTitleStatus {
	public ProjectTitleStatus(String title2, ProjectStatus status2) {
		// TODO Auto-generated constructor stub
	}
	private String title;
	private ProjectStatus status;
}
