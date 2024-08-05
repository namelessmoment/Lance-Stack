package com.lancestack.dto;

import com.lancestack.entities.ProjectStatus;
import com.lancestack.entities.ProjectType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectsByUserDTO {
	@Column
	private String title;
	@Enumerated(EnumType.STRING)
	@Column
	private ProjectType projType;
	@Column
	private String description;
	@Enumerated(EnumType.STRING)
	@Column
	private ProjectStatus status;
	@Column
	private double budget;
}






