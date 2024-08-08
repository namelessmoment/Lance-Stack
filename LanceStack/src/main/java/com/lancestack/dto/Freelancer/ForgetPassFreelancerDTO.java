package com.lancestack.dto.Freelancer;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForgetPassFreelancerDTO {
	@NotBlank(message = "Enter Email")
	private String email;
	@NotBlank(message = "Enter Password")
	private String password;
}
