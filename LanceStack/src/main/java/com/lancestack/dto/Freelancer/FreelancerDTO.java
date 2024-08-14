package com.lancestack.dto.Freelancer;

import com.lancestack.dto.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreelancerDTO extends BaseEntity {
	private String freelancerName;
	private String email;
	private String profileDescription;
	private String skills;
	private String mobileNumber;
}
