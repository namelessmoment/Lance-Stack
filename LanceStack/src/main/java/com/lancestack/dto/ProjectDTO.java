package com.lancestack.dto;

import com.lancestack.entities.ProjectStatus;
import com.lancestack.entities.ProjectType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO extends BaseEntity {
	private String title;
	private ProjectType projType;
	private String description;
	private ProjectStatus status;
	private double budget;
}
