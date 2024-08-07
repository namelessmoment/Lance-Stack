package com.lancestack.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Bid.BidDTO;
import com.lancestack.dto.Bid.UpdateBidDTO;
import com.lancestack.dto.Freelancer.FreelancerBidDTO;
import com.lancestack.entities.Bid;
import com.lancestack.entities.Freelancer;
import com.lancestack.entities.Project;
import com.lancestack.repository.BidRepository;
import com.lancestack.repository.FreelancerRepository;
import com.lancestack.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BidServiceImpl implements BidService {
	
	@Autowired
	BidRepository bidRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	FreelancerRepository freelancerRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ApiResponse postBid(BidDTO bidDTO) {
		String msg = "Bid Not Posted!";
		if(bidDTO == null) {
			msg = "Bid is Null!";
		}
		else {
			Bid bid = modelMapper.map(bidDTO, Bid.class);
		    
		    Project project = projectRepo.findById(bidDTO.getProjectId())
		            .orElseThrow(() -> new ResourceNotFound("Project not found"));
//		    bid.setProject(project);    //If not used Helper methods
		    project.addBid(bid);
		    
		    Freelancer freelancer = freelancerRepo.findById(bidDTO.getFreelancerId())
		            .orElseThrow(() -> new ResourceNotFound("Freelancer not found"));
//		    bid.setFreelancer(freelancer);
		    freelancer.addBid(bid);
		    
		    bidRepo.save(bid);
		    msg = "Bid is Posted successfully";
		}
		
//	    return modelMapper.map(savedBid, BidDTO.class);
	    return new ApiResponse(msg);
	}


	@Override
	public List<Bid> getAllBidsByProject(Long projectId) {
		Project proj = projectRepo.findById(projectId)
				.orElseThrow(() -> new RuntimeException("Project not found"));
		return proj.getAllBids();
	}


	@Override
	public ApiResponse updateBid(Long bidId, UpdateBidDTO updatedBid) {
		String msg = "Bid Update Failed!";
		if(updatedBid == null) {
			throw new ResourceNotFound("Bid is Null");
		}
		
		Bid existingBid = bidRepo.findById(bidId)
	            .orElseThrow(() -> new ResourceNotFound("Bid not found"));
	    if(existingBid == null) {
	    	throw new ResourceNotFound("Bid not found");
	    }
	    modelMapper.map(updatedBid, existingBid);
	    bidRepo.save(existingBid);

		return new ApiResponse(msg);
	}


	@Override
	public ApiResponse deleteBid(Long bidId) {
		String msg = "Bid Not Found!";
		if(bidRepo.existsById(bidId)) {
			Bid deleteBid = bidRepo.findById(bidId)
		            .orElseThrow(() -> new ResourceNotFound("Bid not found"));
			bidRepo.delete(deleteBid);
			msg = "Bid is Deleted";
		}
		return new ApiResponse(msg);
	}


	@Override
	public List<FreelancerBidDTO> getAllBidsByFreelancerId(Long freelancerId) {
		List<Bid> freelancerAssocaitedBids = null;
		if(freelancerRepo.existsById(freelancerId)) {
			Freelancer freelancer = freelancerRepo.findById(freelancerId)
		            .orElseThrow(() -> new ResourceNotFound("Freelancer not found"));
			freelancerAssocaitedBids = bidRepo.findByFreelancer(freelancer);
		}
		return modelMapper.map(freelancerAssocaitedBids, new TypeToken<List<FreelancerBidDTO>>() {}.getType());

	}

	
//	@Override
//	public ApiResponse postBid(Bid bid) {
//		String msg = "Bid Post failed!";
//		if(bid == null) {
//			msg = "Bid contains Null!";
//			throw new ResourceNotFound("Bid is Invalid!");
//		}
//		
//		Optional<Freelancer> freelancer = freelancerRepo.findById(bid.getFreelancer().getId());
//		if(freelancer.isEmpty()) {
//			msg = "Freelancer Id contains Null!";
//			throw new ResourceNotFound("Freelancer Id is Invalid!");
//		}
//		bidRepo.save(bid);
//		msg = "Bid Posted...";
//		return new ApiResponse(msg);
//	} 
}
