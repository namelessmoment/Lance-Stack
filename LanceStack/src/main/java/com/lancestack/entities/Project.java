package com.lancestack.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "projects")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity {
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
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
