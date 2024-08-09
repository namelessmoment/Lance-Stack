package com.lancestack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Bid;
import com.lancestack.entities.Freelancer;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
	List<Bid> findByFreelancer(Freelancer freelancer);
	
//	@Query("SELECT b FROM Bid b WHERE (link unavailable) = :projectId")
//    List<Bid> findByProjectId(@Param("projectId") Long projectId);
	

	 @Query(value = "SELECT * FROM bids WHERE project_id = ?", nativeQuery = true)
	 List<Bid> findByProjectId(Long projectId);



}
