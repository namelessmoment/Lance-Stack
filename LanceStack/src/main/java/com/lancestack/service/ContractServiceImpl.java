package com.lancestack.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFoundException;
import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Contract.ContractDTO;
import com.lancestack.dto.Contract.ContractRegistrationDTO;
import com.lancestack.dto.Contract.FindInProgressContractByUserResponseDTO;
import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.entities.Contract;
import com.lancestack.entities.ContractStatus;
import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectStatus;
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
			throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST,"Contract is Null!");
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
			throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST,"Contract Id is Null!");
		}
		else {
			Contract contract = contractRepo.findById(contractId)
		            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Contract not found!"));
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
			throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST,"Days provided Null!");
		}
		else {
			Contract contract = contractRepo.findById(contractId)
		            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Contract not found!"));
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
	public List<ContractDTO> getAllInProcessContractsByUserId(Long UserId) {
		
		 List<Contract> contracts = contractRepo.findAllContractsInProgressByUserId(UserId);

	        // Map the list of Contract entities to ContractDTO
	        return contracts.stream()
	            .map(contract -> {
	                ProjectDTO projectDTO = new ProjectDTO();
	                projectDTO.setTitle(contract.getProject().getTitle());
	                projectDTO.setProjType(contract.getProject().getProjType());
	                projectDTO.setDescription(contract.getProject().getDescription());
	                projectDTO.setStatus(contract.getProject().getStatus());
	                projectDTO.setBudget(contract.getProject().getBudget());
	                
	                FreelancerDTO freelancerDto = new FreelancerDTO();
	                freelancerDto.setFreelancerName(contract.getFreelancer().getFreelancerName());
	                // other details left null as they are not needed.

	                ContractDTO contractDTO = new ContractDTO();
	                contractDTO.setId(contract.getId());
	                contractDTO.setStartDate(contract.getStartDate());
	                contractDTO.setEndDate(contract.getEndDate());
	                contractDTO.setPaymentAmount(contract.getPaymentAmount());
	                contractDTO.setStatus(contract.getStatus());
	                contractDTO.setProject(projectDTO);  // Include ProjectDTO
	                contractDTO.setFreelancer(freelancerDto); // include freelancerDto

	                return contractDTO;
	            })
	            .collect(Collectors.toList());
	    }

	@Override
	public List<ContractDTO> getAllCompletedContractsByUserId(Long UserId) {
		// TODO Auto-generated method stub
		List<Contract> contracts = contractRepo.findAllContractsCompletedByUserId(UserId);

        // Map the list of Contract entities to ContractDTO
        return contracts.stream()
            .map(contract -> {
                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setTitle(contract.getProject().getTitle());
                projectDTO.setProjType(contract.getProject().getProjType());
                projectDTO.setDescription(contract.getProject().getDescription());
                projectDTO.setStatus(contract.getProject().getStatus());
                projectDTO.setBudget(contract.getProject().getBudget());

                FreelancerDTO freelancerDto = new FreelancerDTO();
                freelancerDto.setFreelancerName(contract.getFreelancer().getFreelancerName());
                
                ContractDTO contractDTO = new ContractDTO();
                contractDTO.setStartDate(contract.getStartDate());
                contractDTO.setEndDate(contract.getEndDate());
                contractDTO.setPaymentAmount(contract.getPaymentAmount());
                contractDTO.setStatus(contract.getStatus());
                contractDTO.setProject(projectDTO);  // Include ProjectDTO
                contractDTO.setFreelancer(freelancerDto);

                return contractDTO;
            })
            .collect(Collectors.toList());
	}
	
//	public List<FindInProgressContractByUserResponseDTO> getAllContractsByUserInProgress(Long userId) {
//		User user = userRepo.findById(userId)
//	            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User Id is Invalid"));
//	    List<Contract> contracts = contractRepo.findContractsByUser(user);
//	    List<FindInProgressContractByUserResponseDTO> contractsDTO = new ArrayList<>();
//	    for (Contract contract : contracts) {
//	    	if(contract.getStatus() == ContractStatus.IN_PROGRESS)
//	    		contractsDTO.add(mapContractToResponseDTO(contract));
//	    }
//	    return contractsDTO;
//	}
	
//	@Override
//	public List<FindInProgressContractByUserResponseDTO> getAllContractsByUserCompleted(Long userId) {
//		User user = userRepo.findById(userId)
//	            .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User Id is Invalid"));
//	    List<Contract> contracts = contractRepo.findContractsByUser(user);
//	    List<FindInProgressContractByUserResponseDTO> contractsDTO = new ArrayList<>();
//	    for (Contract contract : contracts) {
//	    	if(contract.getStatus() == ContractStatus.COMPLETED)
//	    		contractsDTO.add(mapContractToResponseDTO(contract));
//	    }
//	    return contractsDTO;
//	}
	
	public FindInProgressContractByUserResponseDTO mapContractToResponseDTO(Contract contract) {
		FindInProgressContractByUserResponseDTO responseDTO = new FindInProgressContractByUserResponseDTO();
	    responseDTO.setId(contract.getId());
	    responseDTO.setPaymentAmount(contract.getPaymentAmount());
	    responseDTO.setStatus(contract.getStatus());
	    
	    FreelancerDTO freelancerDTO = new FreelancerDTO();
	    freelancerDTO.setFreelancerName(contract.getFreelancer().getFreelancerName());
	    freelancerDTO.setEmail(contract.getFreelancer().getEmail());
	    freelancerDTO.setProfileDescription(contract.getFreelancer().getProfileDescription());
	    freelancerDTO.setSkills(contract.getFreelancer().getSkills());
	    // setting freelancer
	    responseDTO.setFreelancer(freelancerDTO);
	    return responseDTO;
	}

	@Override
	public ApiResponse deleteContract(Long contractId) {
		
		Contract contract = contractRepo.findById(contractId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Invalid COntract Id"));
		contractRepo.delete(contract);
		return  new ApiResponse("User Deleted Successfully");
	}

}
