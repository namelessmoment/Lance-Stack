package com.lancestack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Bid;
import com.lancestack.entities.Freelancer;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
	List<Bid> findByFreelancer(Freelancer freelancer);
}
