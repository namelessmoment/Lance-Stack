package com.lancestack.service;

import java.util.Map;

import com.lancestack.dto.Payment.CreateOrderRequestPaymentDTO;
import com.lancestack.dto.Payment.PaymentDTO;
import com.razorpay.RazorpayException;

public interface PaymentService {
	
	PaymentDTO createOrder(CreateOrderRequestPaymentDTO amount) throws RazorpayException;
	PaymentDTO updateOrder(Map<String, String> responsePayload);
	PaymentDTO updateOrderForFailure(Map<String, String> responsePayload);
}
