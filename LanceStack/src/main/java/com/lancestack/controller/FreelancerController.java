package com.lancestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Freelancer.ForgetPassFreelancerDTO;
import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.dto.Freelancer.FreelancerLoginDTO;
import com.lancestack.dto.Freelancer.FreelancerRegistrationDTO;
import com.lancestack.service.FreelancerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("freelancers")
public class FreelancerController {
	
	@Autowired
	FreelancerService freelancerService;
	
	@Operation(description = "Get List of All Freelancers.")
	@GetMapping
	public ResponseEntity<List<FreelancerDTO>> getAllFreelancers(){
		return ResponseEntity
				.ok(freelancerService.getAllFreelancers());
	}
	
	@Operation(description = "Registeration Endpoint for Freelancer.")
	@PostMapping
	public ResponseEntity<?> registerFreelancer(@RequestBody FreelancerRegistrationDTO freelancer){
		try {
			return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(freelancerService.registerFreelancer(freelancer));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Updating Freelancer Details.")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateFreelancer(@PathVariable Long id , @RequestBody FreelancerRegistrationDTO freelancerDTO){
		try {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(freelancerService.updateFreelancerDetails(id, freelancerDTO));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Get Freelancer Details By their ID.")
	@GetMapping("/{id}")
    public ResponseEntity<FreelancerDTO> getFreelancerById(@PathVariable Long id) {
        FreelancerDTO freelancer = freelancerService.getFreelancerById(id);
        return ResponseEntity.ok(freelancer);
    }
	
	@Operation(description = "Get Freelancer by Email.")
	@GetMapping("/email/{fEmail}")
	public ResponseEntity<FreelancerDTO> getFreelancerByEmail(@PathVariable String fEmail){
		FreelancerDTO freelancer = freelancerService.getFreelancerByEmail(fEmail);
		return ResponseEntity.ok(freelancer);
	}
	
	@Operation(description = "Freelancer Login Endpoint.")
	@PostMapping("/freelancerLogin")
	public ResponseEntity<FreelancerDTO> getFreelancerByEmailAndPassword(@RequestBody FreelancerLoginDTO freelancerLoginDTO){
	    FreelancerDTO freelancer = freelancerService.getFreelancerByEmailAndPassword(freelancerLoginDTO);
	    return ResponseEntity.ok(freelancer);
	}
	
	@Operation(description = "Freelancer Forget Password Endpoint.")
	@PatchMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody ForgetPassFreelancerDTO freelancerDTO){
		try {
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(freelancerService.forgetPassWord(freelancerDTO));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
}
