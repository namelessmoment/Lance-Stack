package com.lancestack.service;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Rating.FreelancerToUserRatingDTO;
import com.lancestack.dto.Rating.UserToFreelancerRatingDTO;

public interface RatingService {

	ApiResponse givingRatingToFreelancer(UserToFreelancerRatingDTO rating);

	ApiResponse givingRatingToUser(FreelancerToUserRatingDTO rating);
	
}
