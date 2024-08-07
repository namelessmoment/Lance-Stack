package com.lancestack.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Rating.FreelancerToUserRatingDTO;
import com.lancestack.dto.Rating.UserToFreelancerRatingDTO;
import com.lancestack.entities.Contract;
import com.lancestack.entities.Freelancer;
import com.lancestack.entities.Rating;
import com.lancestack.entities.RatingType;
import com.lancestack.entities.User;
import com.lancestack.repository.ContractRepository;
import com.lancestack.repository.FreelancerRepository;
import com.lancestack.repository.Ratingrepository;
import com.lancestack.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
	
	@Autowired
	Ratingrepository ratingRepo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@Autowired
	FreelancerRepository freelancerRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ApiResponse givingRatingToFreelancer(UserToFreelancerRatingDTO ratingDTO) {
		String msg = "Rating To Freelancer Failed!";
		if(ratingDTO == null) {
			msg = "Rating body is Null!";
		}
		else {
			Contract contract =contractRepo.findById(ratingDTO.getContract())
					.orElseThrow();
			User user = userRepo.findById(ratingDTO.getRater()).orElseThrow();
			Freelancer freelancer = freelancerRepo.findById(ratingDTO.getRatee())
					.orElseThrow();
			Rating rating = modelMapper.map(ratingDTO, Rating.class);
			rating.setContract(contract);
			rating.setRater(user);
			rating.setRatee(freelancer);
			rating.setRatingType(RatingType.USER_TO_FREELANCER);
			ratingRepo.save(rating);
			msg = "Rating To Freelancer is Successful";
		}
		return new ApiResponse(msg);
	}

	@Override
	public ApiResponse givingRatingToUser(FreelancerToUserRatingDTO ratingDTO) {
		String msg = "Rating To User Failed!";
		if(ratingDTO == null) {
			msg = "Rating body is Null!";
		}
		else {
			Contract contract =contractRepo.findById(ratingDTO.getContract())
					.orElseThrow();
			User user = userRepo.findById(ratingDTO.getRatee()).orElseThrow();
			Freelancer freelancer = freelancerRepo.findById(ratingDTO.getRater())
					.orElseThrow();
			Rating rating = modelMapper.map(ratingDTO, Rating.class);
			rating.setContract(contract);
			rating.setRater(user);
			rating.setRatee(freelancer);
			rating.setRatingType(RatingType.FREELANCER_TO_USER);
			ratingRepo.save(rating);
			msg = "Rating To User is Successful";
		}
		return new ApiResponse(msg);
	}
}
