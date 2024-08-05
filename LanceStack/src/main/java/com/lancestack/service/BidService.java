package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.BidDTO;
import com.lancestack.dto.FreelancerBidDTO;
import com.lancestack.dto.UpdateBidDTO;
import com.lancestack.entities.Bid;

public interface BidService {

	BidDTO postBid(BidDTO bidDTO);
	
	List<Bid> getAllBidsByProject(Long projectId);

	ApiResponse updateBid(Long id, UpdateBidDTO updatedBid);

	ApiResponse deleteBid(Long bidId);

	List<FreelancerBidDTO> getAllBidsByFreelancerId(Long freelancerId);
	
}
