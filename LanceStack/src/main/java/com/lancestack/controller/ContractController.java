package com.lancestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Contract.ContractDTO;
import com.lancestack.dto.Contract.ContractRegistrationDTO;
import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.service.ContractService;
import com.lancestack.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("contracts")
public class ContractController {
	
	@Autowired
	ContractService contractService;
	
	@Autowired
	ProjectService projectService;
	
	@Operation(description = "Create Contract Endpoint")
	@PostMapping("/createContract")
	public ResponseEntity<?> createContract(@RequestBody ContractRegistrationDTO contractDTO){
		try {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(contractService.createContract(contractDTO));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Change Status Contract to completed Endpoint")
	@PatchMapping("/changeStatus/{contractId}")
	public ResponseEntity<?> changeContractStatusToCompleted(@PathVariable Long contractId){
		try {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(contractService.changeContractStatusToCompleted(contractId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Change duration of Contract Endpoint")
	@PatchMapping("/{contractId}/{days}")
	public ResponseEntity<?> changeContractDuration(@PathVariable Long contractId, @PathVariable Integer days){
		try {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(contractService.changeContractDuration(contractId, days));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Get All IN_PROCESS Contracts")
	@GetMapping
	public ResponseEntity<List<ContractDTO>> getAllInProcessContracts(){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(contractService.getAllInProcessContracts());
	}
	
	@Operation(description = "Get Completed Contracts's Project Details")
	@GetMapping("/projects/completed-contracts")
	public ResponseEntity<List<ProjectDTO>> getProjectsByCompletedContracts() {
	    List<ProjectDTO> projects = projectService.getProjectsByCompletedContracts();
	    return ResponseEntity.ok(projects);
	}
	
	@Operation(description = "Get all IN_PROCESS Contracts by freelancerId")
    @GetMapping("/inProgress/freelancer/{freelancerId}")
    public ResponseEntity<List<ContractDTO>> getAllInProgressContractsByFreelancerId(@PathVariable Long freelancerId) {
        // Call the service method to get the contracts
        List<ContractDTO> contracts = contractService.getAllInProcessContractsByFreelancerId(freelancerId);

        // Return the list of contracts
        return ResponseEntity.ok(contracts);
    }
	
	@Operation(description = "Get all COMPLETED Contracts by freelancerId")
    @GetMapping("/completed/freelancer/{freelancerId}")
    public ResponseEntity<List<ContractDTO>> getAllCompletedContractsByFreelancerId(@PathVariable Long freelancerId) {
        // Call the service method to get the contracts
        List<ContractDTO> contracts = contractService.getAllCompletedContractsByFreelancerId(freelancerId);

        // Return the list of contracts
        return ResponseEntity.ok(contracts);
    }

}
