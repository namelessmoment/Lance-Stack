package com.lancestack.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileUser {
	private String userName;
	private String email;
	private String oldPassword;
	private String mobileNumber;
	private String newPassword;
}
