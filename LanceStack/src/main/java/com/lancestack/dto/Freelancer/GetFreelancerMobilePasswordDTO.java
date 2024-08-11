package com.lancestack.dto.Freelancer;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GetFreelancerMobilePasswordDTO {
	private String password;
	private String mobileNumber;
}
