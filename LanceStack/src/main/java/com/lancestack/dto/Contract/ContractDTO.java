package com.lancestack.dto.Contract;

import java.time.LocalDate;

import com.lancestack.dto.BaseEntity;
import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.entities.ContractStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO extends BaseEntity{
	private ProjectDTO project;
    private LocalDate startDate;
    private LocalDate endDate;
    private double paymentAmount;
	private ContractStatus status;
	private FreelancerDTO freelancer;
}
