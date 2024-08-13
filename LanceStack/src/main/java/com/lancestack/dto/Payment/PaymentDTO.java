package com.lancestack.dto.Payment;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
//	private Long contractId;
	private LocalDateTime paymentDate;
	private Integer paymentAmount;
	private String paymentMethod;
	private String paymentStatus;
	private String razorPayOrderId;
}
