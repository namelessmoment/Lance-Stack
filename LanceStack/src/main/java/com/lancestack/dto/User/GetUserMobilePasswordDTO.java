package com.lancestack.dto.User;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserMobilePasswordDTO {
	private String password;
	private String mobileNumber;
}
