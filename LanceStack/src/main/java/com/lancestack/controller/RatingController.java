package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.Rating.FreelancerToUserRatingDTO;
import com.lancestack.dto.Rating.UserToFreelancerRatingDTO;
import com.lancestack.service.RatingService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("ratings")
public class RatingController {
	
	@Autowired
	RatingService ratingService;
	
	@Operation(description = "Give Rating to Freelancer.")
	@PostMapping("/ratingToFreelancer")
	public ResponseEntity<?> givingRatingToFreelancer(@RequestBody UserToFreelancerRatingDTO rating){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ratingService.givingRatingToFreelancer(rating));
	}
	
	@Operation(description = "Give Rating to Freelancer.")
	@PostMapping("/ratingToUser")
	public ResponseEntity<?> givingRatingToUser(@RequestBody FreelancerToUserRatingDTO rating){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ratingService.givingRatingToUser(rating));
	}
}
