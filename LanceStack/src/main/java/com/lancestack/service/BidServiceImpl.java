package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFoundException;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Bid.BidDTO;
import com.lancestack.dto.Bid.UpdateBidDTO;
import com.lancestack.dto.Freelancer.FreelancerBidDTO;
import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.entities.Bid;
import com.lancestack.entities.Freelancer;
import com.lancestack.entities.Project;
import com.lancestack.repository.BidRepository;
import com.lancestack.repository.ContractRepository;
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
	ContractRepository contractRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ApiResponse postBid(BidDTO bidDTO) {
		String msg = "Bid Not Posted!";
		if(bidDTO == null) {
			msg = "Bid is Null!";
		}
		else {
			try {
			Bid bid = modelMapper.map(bidDTO, Bid.class);
		    
		    Project project = projectRepo.findById(bidDTO.getProjectId())
		            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Project not found"));
//		    bid.setProject(project);    //If not used Helper methods
		    project.addBid(bid);
		    
		    Freelancer freelancer = freelancerRepo.findById(bidDTO.getFreelancerId())
		            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Freelancer not found"));
//		    bid.setFreelancer(freelancer);
		    freelancer.addBid(bid);
		    
		    bidRepo.save(bid);
		    msg = "Bid is Posted successfully";
			}catch (RuntimeException e) {
	            // Log the exception for debugging
	            e.printStackTrace();
	            msg = "An error occurred: " + e.getMessage();
	        }
			
		}
		
//	    return modelMapper.map(savedBid, BidDTO.class);
	    return new ApiResponse(msg);
	}


	@Override
	public List<BidDTO> getAllBidsByProject(Long projectId) {
//		Project proj = projectRepo.findById(projectId)
//				.orElseThrow(() -> new RuntimeException("Project not found"));
//		return proj.getAllBids();
		List<Bid> bids = bidRepo.findByProjectId(projectId);
		List<BidDTO> bidDTOs = bids.stream()
		        .map(bid -> {
		            BidDTO bidDTO = modelMapper.map(bid, BidDTO.class);
		            bidDTO.setProjectId(bid.getProject().getId());
		            bidDTO.setFreelancerId(bid.getFreelancer().getId());
		            return bidDTO;
		        })
		        .collect(Collectors.toList());
		return bidDTOs;
	}


	@Override
	public ApiResponse updateBid(Long bidId, UpdateBidDTO updatedBid) {
		String msg = "Bid Update Failed!";
		if(updatedBid == null) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Bid is Null");
		}
		
		Bid existingBid = bidRepo.findById(bidId)
	            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Bid not found"));
	    if(existingBid == null) {
	    	throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Bid not found");
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
		            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Bid not found"));
			bidRepo.delete(deleteBid);
			msg = "Bid is Deleted";
		}
		return new ApiResponse(msg);
	}


	@Override
	public List<FreelancerBidDTO> getAllBidsByFreelancerId(Long freelancerId) {
	    List<Bid> freelancerAssociatedBids = null;
	    if (freelancerRepo.existsById(freelancerId)) {
	        Freelancer freelancer = freelancerRepo.findById(freelancerId)
	                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Freelancer not found"));
	        freelancerAssociatedBids = bidRepo.findByFreelancer(freelancer);
	        
	        // Filter out bids where the project is already in a contract
	        freelancerAssociatedBids = freelancerAssociatedBids.stream()
	                .filter(bid -> !contractRepo.existsByProject(bid.getProject()))
	                .collect(Collectors.toList());
	    }
	    
	    return freelancerAssociatedBids.stream()
	            .map(bid -> {
	                FreelancerBidDTO dto = new FreelancerBidDTO();
	                dto.setId(bid.getId());
	    	        dto.setBidAmount(bid.getBidAmount());
	    	        dto.setDaysWillTake(bid.getDaysWillTake());
	    	        dto.setBidDescription(bid.getBidDescription());
	    	        dto.setProjectId(bid.getProject().getId()); // Set projectId from associated project
	    	        dto.setFreelancerId(bid.getFreelancer().getId()); // Set freelancerId from associated freelancer
	                return dto;
	            })
	            .collect(Collectors.toList());
	}

	
//	@Override
//	public List<FreelancerBidDTO> getAllBidsByFreelancerId(Long freelancerId) {
//		List<Bid> freelancerAssocaitedBids = null;
//		if(freelancerRepo.existsById(freelancerId)) {
//			Freelancer freelancer = freelancerRepo.findById(freelancerId)
//		            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Freelancer not found"));
//			freelancerAssocaitedBids = bidRepo.findByFreelancer(freelancer);
//		}
//		return freelancerAssocaitedBids.stream().map(bid -> {
//	        FreelancerBidDTO dto = new FreelancerBidDTO();
//	        dto.setId(bid.getId());
//	        dto.setBidAmount(bid.getBidAmount());
//	        dto.setDaysWillTake(bid.getDaysWillTake());
//	        dto.setBidDescription(bid.getBidDescription());
//	        dto.setProjectId(bid.getProject().getId()); // Set projectId from associated project
//	        dto.setFreelancerId(bid.getFreelancer().getId()); // Set freelancerId from associated freelancer
//	        return dto;
//	    }).collect(Collectors.toList());
//	}


	@Override
	public FreelancerDTO getFreelanerByBidId(Long bidId) {
		Bid bid  = bidRepo.findById(bidId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Invalid Bid ID"));
		Freelancer freelancer  = bid.getFreelancer();
		FreelancerDTO freelancerDTO = modelMapper.map(freelancer, FreelancerDTO.class); 
		return freelancerDTO;
	}
	
	//after return in above code " modelMapper.map(freelancerAssocaitedBids, new TypeToken<List<FreelancerBidDTO>>() {}.getType());"

	
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
