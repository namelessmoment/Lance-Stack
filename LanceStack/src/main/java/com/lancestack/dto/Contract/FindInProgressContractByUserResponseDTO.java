package com.lancestack.dto.Contract;

import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.entities.BaseEntity;
import com.lancestack.entities.ContractStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindInProgressContractByUserResponseDTO extends BaseEntity{
    private double paymentAmount;
	private ContractStatus status;
	private FreelancerDTO freelancer;
}
