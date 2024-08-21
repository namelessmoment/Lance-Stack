package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
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
		try {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ratingService.givingRatingToFreelancer(rating));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Give Rating to Freelancer.")
	@PostMapping("/ratingToUser")
	public ResponseEntity<?> givingRatingToUser(@RequestBody FreelancerToUserRatingDTO rating){
		try {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ratingService.givingRatingToUser(rating));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Getting Average Rating of Freelancer.")
	@GetMapping("/ratingOfFreelancer/{freelancerId}")
	public ResponseEntity<?> gettingAverageRatingOfFreelancer(@PathVariable Long freelancerId){
		try {
		return  ResponseEntity.ok(ratingService.getAverageRatingOfFreelancer(freelancerId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	
	@Operation(description = "Getting Average Rating of User.")
	@GetMapping("/ratingOfUser/{userId}")
	public ResponseEntity<?> gettingAverageRatingOfUser(@PathVariable Long userId){
		try {
		return  ResponseEntity.ok(ratingService.getAverageRatingForUser(userId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
}
