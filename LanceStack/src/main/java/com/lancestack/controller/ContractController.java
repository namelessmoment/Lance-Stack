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
import com.lancestack.service.ContractService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("contracts")
public class ContractController {
	
	@Autowired
	ContractService contractService;
	
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
	
	@Operation(description = "Change Status Contract Endpoint")
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
}
