package com.lancestack.dto.Freelancer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileFreelancer {
	private String freelancerName;
	private String email;
	private String oldPassword;
	private String mobileNumber;
	private String profileDescription;
	private String skills;
	private String newPassword;
}
