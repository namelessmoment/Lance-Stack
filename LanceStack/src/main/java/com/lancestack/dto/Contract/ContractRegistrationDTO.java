package com.lancestack.dto.Contract;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractRegistrationDTO {
	
    private LocalDate startDate;

    private LocalDate endDate;
    
    private double paymentAmount;
	
	private Long projectId;
	
	private Long freelancerId;
}
