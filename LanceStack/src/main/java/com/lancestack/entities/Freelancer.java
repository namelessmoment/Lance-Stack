package com.lancestack.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "freelancers")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Freelancer extends BaseEntity {
	@Column(length = 40)
	private String freelancerName;
	@Column
	@Email(message = "Please enter a valid email address")
	@NotBlank(message = "Enter Email")
	private String email;
	@Column
	@NotBlank(message = "Enter Password")
	private String password;
	@Column
	private String mobileNumber;
	@Column
	private String profileDescription;
	@Enumerated(EnumType.STRING)
	@Column
	private Skills skills;
}
