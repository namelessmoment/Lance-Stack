package com.lancestack.dto.Contract;

import java.time.LocalDate;

import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.entities.BaseEntity;
import com.lancestack.entities.ContractStatus;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindInProgressContractByFreelancerResponseDTO extends BaseEntity{
    private double paymentAmount;
	private ContractStatus status;
	private FreelancerDTO freelancer;
	private ProjectDTO project;
    private LocalDate startDate;
    private LocalDate endDate;
}
