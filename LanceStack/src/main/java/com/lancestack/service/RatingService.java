package com.lancestack.service;

import java.math.BigDecimal;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Rating.FreelancerToUserRatingDTO;
import com.lancestack.dto.Rating.UserToFreelancerRatingDTO;
import com.lancestack.entities.User;

public interface RatingService {

	ApiResponse givingRatingToFreelancer(UserToFreelancerRatingDTO rating);

	ApiResponse givingRatingToUser(FreelancerToUserRatingDTO rating);
	
	BigDecimal getAverageRatingOfFreelancer(Long freelancerId);
	
}
