package com.lancestack.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.Payment.CreateOrderRequestPaymentDTO;
import com.lancestack.dto.Payment.PaymentDTO;
import com.lancestack.service.PaymentService;
import com.razorpay.RazorpayException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("payments")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@Operation(description = "For creating and saving the Razorpay order into database.")
	@PostMapping(value = "/create-order", produces = "application/json")
	@ResponseBody
	public ResponseEntity<PaymentDTO> createOrder(@RequestBody CreateOrderRequestPaymentDTO order) throws RazorpayException{
		PaymentDTO paymentOrder = paymentService.createOrder(order);
		return new ResponseEntity<>(paymentOrder, HttpStatus.CREATED);
	}
	
	@Operation(description = "To update success the order details after payment.")
	@PostMapping("/handle-payment-callback-success")
	public ResponseEntity<?> updateOrderForSuccess(@RequestParam Map<String, String> responsePayload) {
		
	    // Validate response payload
	    if (responsePayload == null || responsePayload.isEmpty()) {
	        return ResponseEntity.badRequest().body("Invalid response payload");
	    }

	    // Validate Razorpay order ID
	    String razorPayOrderId = responsePayload.get("razor_pay_order_id");
	    if (StringUtils.isEmpty(razorPayOrderId)) {
	        return ResponseEntity.badRequest().body("Razorpay order ID is missing");
	    }

	    // Call service method to update order
	    try {
	        PaymentDTO updatedDTO = paymentService.updateOrder(responsePayload);
	        return ResponseEntity.ok(updatedDTO);
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("Error updating order: " + e.getMessage());
	    }
	}
	
	
	@Operation(description = "To update the order failure details after payment.")
	@PostMapping("/handle-payment-callback-failure")
	public ResponseEntity<?> updateOrderForFailure(@RequestParam Map<String, String> responsePayload) {
	    // Validate response payload
	    if (responsePayload == null || responsePayload.isEmpty()) {
	        return ResponseEntity.badRequest().body("Invalid response payload");
	    }

	    // Validate Razorpay order ID
	    String razorPayOrderId = responsePayload.get("razor_pay_order_id");
	    if (StringUtils.isEmpty(razorPayOrderId)) {
	        return ResponseEntity.badRequest().body("Razorpay order ID is missing");
	    }

	    // Call service method to update order
	    try {
	        PaymentDTO updatedDTO = paymentService.updateOrderForFailure(responsePayload);
	        return ResponseEntity.ok(updatedDTO);
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("Error updating order: " + e.getMessage());
	    }
	}

}
