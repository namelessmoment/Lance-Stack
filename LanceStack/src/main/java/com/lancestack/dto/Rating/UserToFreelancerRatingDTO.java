package com.lancestack.dto.Rating;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserToFreelancerRatingDTO {
	
	private BigDecimal  rating;
	private String feedback;
    private Long contract;
    private Long user;  //User
    private Long freelancer; //Freelancer
}
