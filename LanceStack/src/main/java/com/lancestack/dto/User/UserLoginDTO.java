package com.lancestack.dto.User;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
	@Column
	@Email(message = "Please enter a valid email address")
	@NotBlank(message = "Enter Email")
	private String email;
	@Column
	@NotBlank(message = "Enter Password")
	private String password;
}	
