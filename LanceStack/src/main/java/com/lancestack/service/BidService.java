package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Bid.BidDTO;
import com.lancestack.dto.Bid.UpdateBidDTO;
import com.lancestack.dto.Freelancer.FreelancerBidDTO;
import com.lancestack.dto.Freelancer.FreelancerDTO;

public interface BidService {

	ApiResponse postBid(BidDTO bidDTO);
	
	List<BidDTO> getAllBidsByProject(Long projectId);

	ApiResponse updateBid(Long id, UpdateBidDTO updatedBid);

	ApiResponse deleteBid(Long bidId);

	List<FreelancerBidDTO> getAllBidsByFreelancerId(Long freelancerId);
	
	FreelancerDTO getFreelanerByBidId(Long bidId);
}
