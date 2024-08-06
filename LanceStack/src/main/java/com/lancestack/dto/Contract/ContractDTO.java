package com.lancestack.dto.Contract;

import java.time.LocalDate;

import com.lancestack.dto.BaseEntity;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private double paymentAmount;
	private ContractStatus status;
}
