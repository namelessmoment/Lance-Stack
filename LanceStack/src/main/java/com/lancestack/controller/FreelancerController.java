package com.lancestack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.FreelancerDTO;
import com.lancestack.dto.FreelancerLoginDTO;
import com.lancestack.dto.FreelancerRegistrationDTO;
import com.lancestack.service.FreelancerService;

@RestController
@RequestMapping("freelancers")
public class FreelancerController {
	
	@Autowired
	FreelancerService freelancerService;
	
	@GetMapping
	public ResponseEntity<?> getAllFreelancers(){
		return ResponseEntity
				.ok(freelancerService.getAllFreelancers());
	}
	
	@PostMapping
	public ResponseEntity<?> registerFreelancer(@RequestBody FreelancerRegistrationDTO freelancer){
			return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(freelancerService.registerFreelancer(freelancer));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateFreelancer(@PathVariable Long id , @RequestBody FreelancerRegistrationDTO freelancerDTO){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(freelancerService.updateFreelancerDetails(id, freelancerDTO));
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<FreelancerDTO> getFreelancerById(@PathVariable Long id) {
        FreelancerDTO freelancer = freelancerService.getFreelancerById(id);
        return ResponseEntity.ok(freelancer);
    }
	
	@GetMapping("/email/{fEmail}")
	public ResponseEntity<FreelancerDTO> getFreelancerByEmail(@PathVariable String fEmail){
		FreelancerDTO freelancer = freelancerService.getFreelancerByEmail(fEmail);
		return ResponseEntity.ok(freelancer);
	}
	
	@PostMapping("/freelancerLogin")
	public ResponseEntity<FreelancerDTO> getFreelancerByEmailAndPassword(@RequestBody FreelancerLoginDTO freelancerLoginDTO){
	    FreelancerDTO freelancer = freelancerService.getFreelancerByEmailAndPassword(freelancerLoginDTO);
	    return ResponseEntity.ok(freelancer);
	}
	
	
}
