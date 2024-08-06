package com.lancestack.dto.User;

import com.lancestack.dto.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseEntity{

	private String userName;
	private String email;
	private String password;
	private String mobileNumber;

}
