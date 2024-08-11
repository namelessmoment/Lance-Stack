package com.lancestack.service;

import java.util.List;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Contract.ContractDTO;
import com.lancestack.dto.Contract.ContractRegistrationDTO;

public interface ContractService {

	ApiResponse createContract(ContractRegistrationDTO contractDTO);

	ApiResponse changeContractStatusToCompleted(Long contractId);

	ApiResponse changeContractDuration(Long contractId, Integer days);

	List<ContractDTO> getAllInProcessContracts();

	List<ContractDTO> getAllContractsByUser(Long userId);

}
