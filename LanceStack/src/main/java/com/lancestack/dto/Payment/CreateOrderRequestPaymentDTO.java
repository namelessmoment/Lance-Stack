package com.lancestack.dto.Payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequestPaymentDTO {
	private Long contractId;
	private Integer paymentAmount;
}
