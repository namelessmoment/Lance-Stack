package com.lancestack.dto.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
	@NotBlank(message = "Enter Email")
	private String email;
	@NotBlank(message = "Enter Password")
	private String password;
}	
