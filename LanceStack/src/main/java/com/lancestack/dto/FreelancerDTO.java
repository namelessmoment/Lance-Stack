package com.lancestack.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreelancerDTO extends BaseEntity {
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
	@Column
	private String skills;
}
