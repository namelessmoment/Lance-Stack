package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFound;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Contract.ContractDTO;
import com.lancestack.dto.Contract.ContractRegistrationDTO;
import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.entities.Contract;
import com.lancestack.entities.ContractStatus;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectStatus;
import com.lancestack.entities.User;
import com.lancestack.repository.ContractRepository;
import com.lancestack.repository.FreelancerRepository;
import com.lancestack.repository.ProjectRepository;
import com.lancestack.repository.UserRepository;

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
	UserRepository userRepo;
	
	@Autowired
	ModelMapper modelMapper;

	// Creating Contract
	@Override
	public ApiResponse createContract(ContractRegistrationDTO contractDTO) {
		String msg = "Contract Creation Failed";
		// Check if contract contains null
		if(contractDTO == null) {
			throw new ResourceNotFound(HttpStatus.BAD_REQUEST,"Contract is Null!");
		}
		else {
			Contract contract = modelMapper.map(contractDTO, Contract.class);
		    contract.setStatus(ContractStatus.IN_PROGRESS);
		    Project project = projectRepo.findById(contractDTO.getProjectId()).orElseThrow();
		    project.setStatus(ProjectStatus.IN_PROGRESS);
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
			throw new ResourceNotFound(HttpStatus.BAD_REQUEST,"Contract Id is Null!");
		}
		else {
			Contract contract = contractRepo.findById(contractId)
		            .orElseThrow(() -> new ResourceNotFound(HttpStatus.NOT_FOUND,"Contract not found!"));
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
			throw new ResourceNotFound(HttpStatus.BAD_REQUEST,"Days provided Null!");
		}
		else {
			Contract contract = contractRepo.findById(contractId)
		            .orElseThrow(() -> new ResourceNotFound(HttpStatus.NOT_FOUND,"Contract not found!"));
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

	@Override
	public List<ContractDTO> getAllInProcessContractsByFreelancerId(Long FreelancerId) {
		
		 List<Contract> contracts = contractRepo.findAllContractsInProgressByFreelancerId(FreelancerId);

	        // Map the list of Contract entities to ContractDTO
	        return contracts.stream()
	            .map(contract -> {
	                ProjectDTO projectDTO = new ProjectDTO();
	                projectDTO.setTitle(contract.getProject().getTitle());
	                projectDTO.setProjType(contract.getProject().getProjType());
	                projectDTO.setDescription(contract.getProject().getDescription());
	                projectDTO.setStatus(contract.getProject().getStatus());
	                projectDTO.setBudget(contract.getProject().getBudget());

	                ContractDTO contractDTO = new ContractDTO();
	                contractDTO.setStartDate(contract.getStartDate());
	                contractDTO.setEndDate(contract.getEndDate());
	                contractDTO.setPaymentAmount(contract.getPaymentAmount());
	                contractDTO.setStatus(contract.getStatus());
	                contractDTO.setProject(projectDTO);  // Include ProjectDTO

	                return contractDTO;
	            })
	            .collect(Collectors.toList());
	    }

	@Override
	public List<ContractDTO> getAllCompletedContractsByFreelancerId(Long FreelancerId) {
		// TODO Auto-generated method stub
		List<Contract> contracts = contractRepo.findAllContractsCompletedByFreelancerId(FreelancerId);

        // Map the list of Contract entities to ContractDTO
        return contracts.stream()
            .map(contract -> {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setTitle(contract.getProject().getTitle());
                projectDTO.setProjType(contract.getProject().getProjType());
                projectDTO.setDescription(contract.getProject().getDescription());
                projectDTO.setStatus(contract.getProject().getStatus());
                projectDTO.setBudget(contract.getProject().getBudget());

                ContractDTO contractDTO = new ContractDTO();
                contractDTO.setStartDate(contract.getStartDate());
                contractDTO.setEndDate(contract.getEndDate());
                contractDTO.setPaymentAmount(contract.getPaymentAmount());
                contractDTO.setStatus(contract.getStatus());
                contractDTO.setProject(projectDTO);  // Include ProjectDTO

                return contractDTO;
            })
            .collect(Collectors.toList());
	}
	
	public List<ContractDTO> getAllContractsByUser(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFound(HttpStatus.NOT_FOUND, "User Id is Invalid"));
		List<Contract> contracts = contractRepo.findContractsByUser(user);
	    return contracts.stream()
	            .map(contract -> modelMapper.map(contract, ContractDTO.class))
	            .collect(Collectors.toList());
	}
}
