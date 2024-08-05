package com.lancestack.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.BidDTO;
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
	public BidDTO postBid(BidDTO bidDTO) {
		Bid bid = modelMapper.map(bidDTO, Bid.class);
	    
	    Project project = projectRepo.findById(bidDTO.getProjectId())
	            .orElseThrow(() -> new ResourceNotFound("Project not found"));
	    bid.setProject(project);
	    
	    Freelancer freelancer = freelancerRepo.findById(bidDTO.getFreelancerId())
	            .orElseThrow(() -> new ResourceNotFound("Freelancer not found"));
	    bid.setFreelancer(freelancer);
	    
	    Bid savedBid = bidRepo.save(bid);
	    return modelMapper.map(savedBid, BidDTO.class);

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
