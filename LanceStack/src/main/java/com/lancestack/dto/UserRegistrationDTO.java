package com.lancestack.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegistrationDTO {


		@Column(length = 40)
		private String userName;
		@Column
//		@Email(message = "Please enter a valid email address")
//		@NotBlank(message = "Enter Email")
		private String email;
		@Column
//		@NotBlank(message = "Enter Password")
		private String password;
		@Column
		@Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number. It must be a 10-digit number.")
		private String mobileNumber;
	
}
