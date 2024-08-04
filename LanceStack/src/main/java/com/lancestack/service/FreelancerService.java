package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.FreelancerDTO;
import com.lancestack.dto.FreelancerLoginDTO;
import com.lancestack.dto.FreelancerRegistrationDTO;
import com.lancestack.entities.Freelancer;

public interface FreelancerService {
	public List<Freelancer> getAllFreelancers();
	public ApiResponse registerFreelancer(FreelancerRegistrationDTO freelancer);
	public ApiResponse updateFreelancerDetails(Long id, FreelancerRegistrationDTO freelancer);
	public FreelancerDTO getFreelancerById(Long id);
	public FreelancerDTO getFreelancerByEmail(String fEmail);
	public FreelancerDTO getFreelancerByEmailAndPassword(FreelancerLoginDTO freelancerLoginDTO);
}
