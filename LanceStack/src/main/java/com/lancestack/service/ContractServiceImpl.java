package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Contract.ContractDTO;
import com.lancestack.dto.Contract.ContractRegistrationDTO;
import com.lancestack.entities.Contract;
import com.lancestack.entities.ContractStatus;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectStatus;
import com.lancestack.repository.ContractRepository;
import com.lancestack.repository.FreelancerRepository;
import com.lancestack.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractRepository contractRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	FreelancerRepository freelancerRepo;
	
	@Autowired
	ModelMapper modelMapper;

	// Creating Contract
	@Override
	public ApiResponse createContract(ContractRegistrationDTO contractDTO) {
		String msg = "Contract Creation Failed";
		// Check if contract contains null
		if(contractDTO == null) {
			throw new ResourceNotFound("Contract is Null!");
		}
//		else {
//			Contract contract = modelMapper.map(contractDTO, Contract.class);
//			contract.setStatus(ContractStatus.IN_PROGRESS);
//			Freelancer freelancer = freelancerRepo.findById(contractDTO.getFreelancerId())
//		            .orElseThrow(() -> new ResourceNotFound("Freelancer not found"));
//			// used helper method to add contract in freelancer side.
//			freelancer.addContract(contract);
//			contractRepo.save(contract);
//			msg = "Contract Created Successfully.";
//		}
		else {
			Contract contract = modelMapper.map(contractDTO, Contract.class);
		    contract.setStatus(ContractStatus.IN_PROGRESS);
		    contract.setProject(projectRepo.findById(contractDTO.getProjectId()).orElseThrow());
		    contract.setFreelancer(freelancerRepo.findById(contractDTO.getFreelancerId()).orElseThrow());
		    contractRepo.save(contract);
		    msg = "Contract Created Successfully.";

		}
		
		return new ApiResponse(msg);
	}

	@Override
	public ApiResponse changeContractStatusToCompleted(Long contractId) {
		String msg = "Contract Status change Failed!";
		if(contractId == null) {
			throw new ResourceNotFound("Contract Id is Null!");
		}
		else {
			Contract contract = contractRepo.findById(contractId)
		            .orElseThrow(() -> new ResourceNotFound("Contract not found!"));
			Project project = contract.getProject();
			project.setStatus(ProjectStatus.COMPLETED);
			contract.setStatus(ContractStatus.COMPLETED);
			msg = "Contract Status change Success";
		}
		return new ApiResponse(msg);
	}

	@Override
	public ApiResponse changeContractDuration(Long contractId,Integer days) {
		String msg = "Contract Duration change Failed!";
		if(days == null) {
			throw new ResourceNotFound("Days provided Null!");
		}
		else {
			Contract contract = contractRepo.findById(contractId)
		            .orElseThrow(() -> new ResourceNotFound("Contract not found!"));
			contract.setEndDate(contract.getStartDate().plusDays(days));
			msg = "Contract Duration change Success";
		}
		return new ApiResponse(msg);
	}

	@Override
	public List<ContractDTO> getAllInProcessContracts() {
		List<Contract> contracts = contractRepo.findAllContractsInProgress();
	    return contracts.stream()
	            .map(contract -> modelMapper.map(contract, ContractDTO.class))
	            .collect(Collectors.toList());

	}
}
