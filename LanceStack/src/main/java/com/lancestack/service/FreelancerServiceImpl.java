package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Freelancer.ForgetPassFreelancerDTO;
import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.dto.Freelancer.FreelancerLoginDTO;
import com.lancestack.dto.Freelancer.FreelancerRegistrationDTO;
import com.lancestack.dto.Freelancer.GetFreelancerMobilePasswordDTO;
import com.lancestack.dto.Freelancer.UpdateProfileFreelancer;
import com.lancestack.entities.Freelancer;
import com.lancestack.entities.User;
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
	public List<FreelancerDTO> getAllFreelancers() {
		List<Freelancer> freelancers = freelancerRepo.findAll();
		return freelancers.stream()
	            .map(freelancer -> modelMapper.map(freelancer, FreelancerDTO.class))
	            .collect(Collectors.toList());
	}

	@Override
	public ApiResponse registerFreelancer(FreelancerRegistrationDTO freelancer) {
		String msg = "Freelancer not valid!";
		Freelancer existingFreelancerByEmail = freelancerRepo.findByEmail(freelancer.getEmail());
		
		if(existingFreelancerByEmail != null ) {
			throw new ResourceNotFound(HttpStatus.BAD_REQUEST, "Email Already Exists!");
		}
		Freelancer existingFreelancerByMobileNumber = freelancerRepo.findByMobileNumber(freelancer.getMobileNumber());
		if(existingFreelancerByMobileNumber != null ) {
			throw new ResourceNotFound(HttpStatus.BAD_REQUEST, "Mobile Already Exists!");
		}
		Freelancer freelancer1 = modelMapper.map(freelancer, Freelancer.class);
		freelancerRepo.save(freelancer1);
		msg = "Freelancer Registered Successfully.";
		return new ApiResponse(msg);
	}

	@Override
	public ApiResponse updateFreelancerDetails(Long id, FreelancerRegistrationDTO freelancer) {
		String msg = "Invalid Freelancer Id";
		Freelancer existingFreelancer = freelancerRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFound(HttpStatus.NOT_FOUND,"Invalid Freelancer Id"));
		
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
		Freelancer freelancer = freelancerRepo.findById(id).orElseThrow(() -> new ResourceNotFound(HttpStatus.NOT_FOUND,"Freelancer Id Not Found!"));
		FreelancerDTO freelancerDto = modelMapper.map(freelancer, FreelancerDTO.class);
		return freelancerDto;
	}

	@Override
	public FreelancerDTO getFreelancerByEmail(String fEmail) {
		Freelancer freelancer = freelancerRepo.findByEmail(fEmail);
		if(freelancer == null) {
			throw new ResourceNotFound(HttpStatus.NOT_FOUND,"Freelancer email not found!");
		}
		FreelancerDTO freelancerDto = modelMapper.map(freelancer, FreelancerDTO.class);
		return freelancerDto;
	}

	@Override
	public FreelancerDTO getFreelancerByEmailAndPassword(FreelancerLoginDTO freelancerLoginDTO) {
		Freelancer freelancer = freelancerRepo.findByEmail(freelancerLoginDTO.getEmail());
	    if(freelancer == null) {
	        throw new ResourceNotFound(HttpStatus.NOT_FOUND, "Freelancer not found with email: " + freelancerLoginDTO.getEmail());
	    }
	    if(!freelancer.getPassword().equals(freelancerLoginDTO.getPassword())) {
	        throw new ResourceNotFound(HttpStatus.NOT_FOUND, "Incorrect Freelancer Password!");
	    }
	    FreelancerDTO freelancerDto = modelMapper.map(freelancer, FreelancerDTO.class);
	    return freelancerDto;
	}

	@Override
	public ApiResponse forgetPassWord(ForgetPassFreelancerDTO freelancerDTO) {
		String msg = "Password Not Changed!";
	    Freelancer existingUser = freelancerRepo.findByEmail(freelancerDTO.getEmail());
	    if (existingUser == null) {
	        throw new ResourceNotFound(HttpStatus.BAD_REQUEST, "User Not Exists!");
	    }
	    existingUser.setPassword(freelancerDTO.getPassword());
	    freelancerRepo.save(existingUser);
	    msg = "Password Changed Successfully!";
	    return new ApiResponse(msg);
	}
	
	@Override
	public ApiResponse deleteUser(Long id) {
		Freelancer freelancer = freelancerRepo.findById(id).orElseThrow(()-> new ResourceNotFound(HttpStatus.BAD_REQUEST,"Invalid Id"));
		freelancerRepo.delete(freelancer);
		return new ApiResponse("Freelancer Deleted Successfully");
	}

	@Override
	public GetFreelancerMobilePasswordDTO sendMobilePassword(Long id) {
		Freelancer freelancer = freelancerRepo.findById(id).orElseThrow(()-> new ResourceNotFound(HttpStatus.BAD_REQUEST,"Invalid Id"));
		GetFreelancerMobilePasswordDTO freelancerMobilePasswordDTO = modelMapper.map(freelancer, GetFreelancerMobilePasswordDTO.class);
		return freelancerMobilePasswordDTO;
	}

	@Override
	public ApiResponse updateFreelancerProfile(Long freelancerId ,UpdateProfileFreelancer updateProfileFreelancer) {
		Freelancer freelancer = freelancerRepo.findById(freelancerId)
                .orElseThrow(() -> new ResourceNotFound(HttpStatus.BAD_REQUEST,"Freelancer not found"));
	    boolean profileUpdated = false;
		// Map the changes from UpdateProfileFreelancerDto to the Freelancer entity
        if (updateProfileFreelancer.getFreelancerName() != null) {
            freelancer.setFreelancerName(updateProfileFreelancer.getFreelancerName());
            profileUpdated = true;
        }
        if (updateProfileFreelancer.getEmail() != null) {
            freelancer.setEmail(updateProfileFreelancer.getEmail());
            profileUpdated = true;
        }
        if (updateProfileFreelancer.getMobileNumber() != null) {
            freelancer.setMobileNumber(updateProfileFreelancer.getMobileNumber());
            profileUpdated = true;
        }
        if (updateProfileFreelancer.getProfileDescription() != null) {
            freelancer.setProfileDescription(updateProfileFreelancer.getProfileDescription());
            profileUpdated = true;
        }
        if (updateProfileFreelancer.getSkills() != null) {
            freelancer.setSkills(updateProfileFreelancer.getSkills());
            profileUpdated = true;
        }
        if (updateProfileFreelancer.getNewPassword() != null && !updateProfileFreelancer.getNewPassword().isEmpty() && 
        		updateProfileFreelancer.getOldPassword().equals(freelancer.getPassword())) {
            freelancer.setPassword(updateProfileFreelancer.getNewPassword());
            profileUpdated = true;
        }
        // Save the updated freelancer entity
        if (profileUpdated) {
            freelancerRepo.save(freelancer);
            return new ApiResponse("Profile updated successfully");
        } else {
            return new ApiResponse("No changes made to the profile");
        }
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
