package com.lancestack.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
			User user = userRepo.findById(ratingDTO.getUser()).orElseThrow();
			Freelancer freelancer = freelancerRepo.findById(ratingDTO.getFreelancer())
					.orElseThrow();
			Rating rating = modelMapper.map(ratingDTO, Rating.class);
			rating.setContract(contract);
			rating.setUser(user);
			rating.setFreelancer(freelancer);
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
			User user = userRepo.findById(ratingDTO.getUser()).orElseThrow();
			Freelancer freelancer = freelancerRepo.findById(ratingDTO.getFreelancer())
					.orElseThrow();
			Rating rating = modelMapper.map(ratingDTO, Rating.class);
			rating.setContract(contract);
			rating.setUser(user);
			rating.setFreelancer(freelancer);
			rating.setRatingType(RatingType.FREELANCER_TO_USER);
			ratingRepo.save(rating);
			msg = "Rating To User is Successful";
		}
		return new ApiResponse(msg);
	}

	@Override
	public BigDecimal getAverageRatingOfFreelancer(Long freelancerId) {
//			Freelancer freelancer = freelancerRepo.findById(freelancerId).orElseThrow(()-> new ResourceNotFound("No user found"));
//			modelMapper()
			
//		if(userId==null) {
//			throw new RuntimeException("Invalid UserId");
//		}
//		else {
//			return ratingRepo.findAverageRatingByFreelancerId(freelancer);
		Freelancer freelancer = freelancerRepo.findById(freelancerId).orElseThrow();
		List<Rating> ratings = ratingRepo.findByFreelancer(freelancer);
	    return calculateAverageRating(ratings);

//		}
	}
	
	@Override
	public BigDecimal getAverageRatingForUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow();
	    List<Rating> ratings = ratingRepo.findByUser(user);
	    return calculateAverageRating(ratings);
	}

	// to calculate rating average
	private BigDecimal calculateAverageRating(List<Rating> ratings) {
	    if (ratings.isEmpty()) {
	        return BigDecimal.ZERO;
	    }
	    BigDecimal sum = BigDecimal.ZERO;
	    for (Rating rating : ratings) {
	        sum = sum.add(rating.getRating());
	    }
	    return sum.divide(BigDecimal.valueOf(ratings.size()), 2, RoundingMode.HALF_UP);
	}

	
	
}
