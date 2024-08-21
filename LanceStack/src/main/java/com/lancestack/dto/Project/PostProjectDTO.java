package com.lancestack.dto.Project;

import com.lancestack.entities.ProjectType;

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
//    private ProjectStatus status;
    private double budget;
    private Long user;

}
