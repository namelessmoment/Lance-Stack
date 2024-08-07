package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.entities.Rating;
import com.lancestack.service.RatingService;

@RestController
@RequestMapping("ratings")
public class RatingController {
	
	@Autowired
	RatingService ratingService;
	
	public ResponseEntity<?> givingRatingToFreelancer(@RequestBody Rating rating){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ratingService.givingRatingToFreelancer(rating));
	}
}
