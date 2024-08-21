package com.lancestack.dto.Contract;

import java.time.LocalDate;

import com.lancestack.dto.Project.ProjectDTO;
import com.lancestack.entities.BaseEntity;
import com.lancestack.entities.ContractStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindContractByUserResponseDTO extends BaseEntity{
	private LocalDate startDate;
    private LocalDate endDate;
    private double paymentAmount;
	private ContractStatus status;
	private ProjectDTO projectDTO;
}
