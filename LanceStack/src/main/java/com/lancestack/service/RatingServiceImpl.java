package com.lancestack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.dto.ApiResponse;
import com.lancestack.entities.Rating;
import com.lancestack.repository.Ratingrepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
	
	@Autowired
	Ratingrepository ratingRepo;

	@Override
	public ApiResponse givingRatingToFreelancer(Rating rating) {
		// TODO Auto-generated method stub
		return null;
	}
}
