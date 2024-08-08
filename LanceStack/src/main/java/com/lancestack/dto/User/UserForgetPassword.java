package com.lancestack.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForgetPassword {
	private String email;
	private String password;
}
