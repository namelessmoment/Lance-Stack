package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.BidDTO;
import com.lancestack.entities.Bid;

public interface BidService {

	BidDTO postBid(BidDTO bidDTO);
	
	List<Bid> getAllBidsByProject(Long projectId);
	
}
