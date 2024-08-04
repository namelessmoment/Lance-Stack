package com.lancestack.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.FreelancerDTO;
import com.lancestack.dto.FreelancerLoginDTO;
import com.lancestack.dto.FreelancerRegistrationDTO;
import com.lancestack.entities.Freelancer;
import com.lancestack.repository.FreelancerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FreelancerServiceImpl implements FreelancerService {

	@Autowired
	FreelancerRepository freelancerRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Freelancer> getAllFreelancers() {
		return freelancerRepo.findAll();
	}

	@Override
	public ApiResponse registerFreelancer(FreelancerRegistrationDTO freelancer) {
		Freelancer freelancer1 = modelMapper.map(freelancer, Freelancer.class);
		freelancerRepo.save(freelancer1);
		return new ApiResponse("Freelancer Registered Successfully.");
	}

	@Override
	public ApiResponse updateFreelancerDetails(Long id, FreelancerRegistrationDTO freelancer) {
		String msg = "Invalid Freelancer Id";
		Freelancer existingFreelancer = freelancerRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFound("Invalid Freelancer Id"));
		
		if(existingFreelancer != null) {
			existingFreelancer.setFreelancerName(freelancer.getFreelancerName());
	        existingFreelancer.setEmail(freelancer.getEmail());
	        existingFreelancer.setPassword(freelancer.getPassword());
	        existingFreelancer.setMobileNumber(freelancer.getMobileNumber());
	        existingFreelancer.setProfileDescription(freelancer.getProfileDescription());
	        existingFreelancer.setSkills(freelancer.getSkills());
			freelancerRepo.save(existingFreelancer);
			msg = "Freelancer Updated.";
		}
		return new ApiResponse(msg);
	}

	@Override
	public FreelancerDTO getFreelancerById(Long id) {
		Freelancer freelancer = freelancerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Freelancer Id Not Found!"));

		FreelancerDTO freelancerDto = modelMapper.map(freelancer, FreelancerDTO.class);
		return freelancerDto;
	}

	@Override
	public FreelancerDTO getFreelancerByEmail(String fEmail) {
		Freelancer freelancer = freelancerRepo.findByEmail(fEmail);
		if(freelancer == null) {
			throw new ResourceNotFound("Freelancer email not found!");
		}
		FreelancerDTO freelancerDto = modelMapper.map(freelancer, FreelancerDTO.class);
		return freelancerDto;
	}

	@Override
	public FreelancerDTO getFreelancerByEmailAndPassword(FreelancerLoginDTO freelancerLoginDTO) {
//		String msg = "Login Failed!";
		Freelancer freelancer = freelancerRepo.findByEmail(freelancerLoginDTO.getEmail());
		if(!freelancer.getPassword().equals(freelancerLoginDTO.getPassword())) {
//			msg = "Incorrect Password!";
			throw new ResourceNotFound("Incorrect Password!");
		}
		FreelancerDTO freelancerDto = modelMapper.map(freelancer, FreelancerDTO.class);
		
		return freelancerDto;
	}
	
//	// Method to convert Freelancer entity to FreelancerDTO
//    private FreelancerRegistrationDTO mapToDTO(Freelancer freelancer) {
//    	FreelancerRegistrationDTO dto = new FreelancerRegistrationDTO();
//        dto.setFreelancerName(freelancer.getFreelancerName());
//        dto.setEmail(freelancer.getEmail());
//        dto.setMobileNumber(freelancer.getMobileNumber());
//        dto.setProfileDescription(freelancer.getProfileDescription());
//        dto.setSkills(freelancer.getSkills());
//        return dto;
//    }
	
}