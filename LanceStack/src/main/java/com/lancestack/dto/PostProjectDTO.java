package com.lancestack.dto;

import com.lancestack.entities.ProjectStatus;
import com.lancestack.entities.ProjectType;
import com.lancestack.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectDTO {
	private String title;
    private ProjectType projType;
    private String description;
    private ProjectStatus status;
    private double budget;
    private User user;
}
