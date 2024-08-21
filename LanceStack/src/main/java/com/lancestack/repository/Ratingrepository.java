package com.lancestack.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Freelancer;
import com.lancestack.entities.Rating;
import com.lancestack.entities.User;

@Repository
public interface Ratingrepository extends JpaRepository<Rating, Long> {
//	@Query("SELECT AVG(r.rating) FROM Rating r WHERE r.user = :user")
//    BigDecimal findAverageRatingByFreelancerId(Freelancer freelancer);
	
	List<Rating> findByFreelancer(Freelancer freelancer);
	List<Rating> findByUser(User user);

}
