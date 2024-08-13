package com.lancestack.dto.Payment;

import java.time.LocalDateTime;

import com.lancestack.dto.Contract.ContractDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
//	private Long contractId;
	private ContractDTO contract;
	private LocalDateTime paymentDateTime;
	private Integer paymentAmount;
	private String paymentMethod;
	private String paymentStatus;
	private String razorPayOrderId;
}
