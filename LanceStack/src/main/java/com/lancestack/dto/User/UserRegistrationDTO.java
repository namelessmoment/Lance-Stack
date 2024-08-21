package com.lancestack.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegistrationDTO {
		private String userName;
		@Email
		private String email;
//		@NotBlank(message = "Enter Password")
		private String password;
		@Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number. It must be a 10-digit number.")
		private String mobileNumber;
	
}
