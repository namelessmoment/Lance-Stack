package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Freelancer.ForgetPassFreelancerDTO;
import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.dto.Freelancer.FreelancerLoginDTO;
import com.lancestack.dto.Freelancer.FreelancerRegistrationDTO;
import com.lancestack.dto.Freelancer.GetFreelancerMobilePasswordDTO;
import com.lancestack.dto.Freelancer.UpdateProfileFreelancer;
import com.lancestack.dto.Project.ProjectDTO;

public interface FreelancerService {
	public List<FreelancerDTO> getAllFreelancers();
	public ApiResponse registerFreelancer(FreelancerRegistrationDTO freelancer);
	public ApiResponse updateFreelancerDetails(Long id, FreelancerRegistrationDTO freelancer);
	public FreelancerDTO getFreelancerById(Long id);
	public FreelancerDTO getFreelancerByEmail(String fEmail);
	public FreelancerDTO getFreelancerByEmailAndPassword(FreelancerLoginDTO freelancerLoginDTO);
	public ApiResponse forgetPassWord(ForgetPassFreelancerDTO freelancerDTO);
	ApiResponse deleteUser(Long id);
	GetFreelancerMobilePasswordDTO sendMobilePassword(Long id);
	ApiResponse updateFreelancerProfile(Long freelancerId ,UpdateProfileFreelancer updateProfileFreelancer);
	List<ProjectDTO> getAllocatedProjectsInProgress(Long freelancerId);
	public List<ProjectDTO> getAllocatedProjectsCompleted(Long freelancerId);
	void markProjectAsCompleted(Long projectId);
}
