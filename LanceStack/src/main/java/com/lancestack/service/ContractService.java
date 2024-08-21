package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Contract.ContractDTO;
import com.lancestack.dto.Contract.ContractRegistrationDTO;
import com.lancestack.dto.Contract.FindInProgressContractByFreelancerResponseDTO;

public interface ContractService {

	ApiResponse createContract(ContractRegistrationDTO contractDTO);

	ApiResponse changeContractStatusToCompleted(Long contractId);

	ApiResponse changeContractDuration(Long contractId, Integer days);

	List<ContractDTO> getAllInProcessContracts();
	
	List<ContractDTO> getAllInProcessContractsByUserId(Long UserId);
	
	List<ContractDTO> getAllCompletedContractsByUserId(Long UserId);

	List<FindInProgressContractByFreelancerResponseDTO> getAllContractsByFreelancerInProgress(Long freelancerId);
	
	ApiResponse deleteContract(Long contractId);

	List<FindInProgressContractByFreelancerResponseDTO> getAllContractsByFreelancerCompleted(Long freelancerId);

}
