package com.lancestack.service;

import com.lancestack.dto.ApiResponse;
import com.lancestack.entities.Rating;

public interface RatingService {

	ApiResponse givingRatingToFreelancer(Rating rating);
	
}
