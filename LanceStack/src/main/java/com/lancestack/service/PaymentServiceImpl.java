package com.lancestack.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lancestack.custom_exception.ResourceNotFoundException;
import com.lancestack.dto.Contract.ContractDTO;
import com.lancestack.dto.Payment.CreateOrderRequestPaymentDTO;
import com.lancestack.dto.Payment.PaymentDTO;
import com.lancestack.entities.Contract;
import com.lancestack.entities.Payment;
import com.lancestack.repository.ContractRepository;
import com.lancestack.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentRepository paymentRepo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@Autowired
	ContractServiceImpl contractService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Value("${razorpay.key.id}")
	private String razorPayKey;
	@Value("${razorpay.secret.key}")
	private String razorPaySecret;
	
	private RazorpayClient client;
	
	@Override
	public PaymentDTO createOrder(CreateOrderRequestPaymentDTO order) throws RazorpayException {
		JSONObject orderReq = new JSONObject();
	    orderReq.put("amount", order.getPaymentAmount() * 100);
	    orderReq.put("currency", "INR");
	    orderReq.put("receipt", "receipt_id");

	    this.client = new RazorpayClient(razorPayKey, razorPaySecret);
	    Order razorPayOrder = client.orders.create(orderReq);
	    System.out.println(razorPayOrder);

	    Payment payment = modelMapper.map(order, Payment.class);
	    payment.setRazorPayOrderId(razorPayOrder.get("id"));
	    payment.setPaymentStatus(razorPayOrder.get("status"));
	    payment.setPaymentMethod(razorPayOrder.get("method"));

	    Contract contract = contractRepo.findById(order.getContractId())
	    		.orElseThrow(() -> new RuntimeException("Contract not found"));
	    payment.setContractId(contract);
	    paymentRepo.save(payment);

//	    // Map the saved payment entity to PaymentDTO
//	    PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
	    PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
	    paymentDTO.setContract(modelMapper.map(contract, ContractDTO.class));
//	    paymentDTO.setPaymentDateTime(payment.setPaymentDateTime(LocalDateTime.now()));
	    return paymentDTO;
	}


	@Override
	public PaymentDTO updateOrder(Map<String, String> responsePayload) {
		String razorPayOrderId = responsePayload.get("razor_pay_order_id");
		Payment order = paymentRepo.findByRazorPayOrderId(razorPayOrderId);
//		ContractService contractService = new ContractServiceImpl(); // this will not work
	    String contractId = responsePayload.get("contractId"); // Get contractId as a String
	    Contract contract = contractRepo.findById(Long.parseLong(contractId))
	    		.orElseThrow(()-> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Contract Id Not found!")); // Parse contractId to Long
	    contractService.changeContractStatusToCompleted(contract.getId());
	    order.setPaymentStatus("Paid");
		Payment updatedOrder = paymentRepo.save(order);
		PaymentDTO updatedDTO = modelMapper.map(updatedOrder, PaymentDTO.class);
		return updatedDTO;
	}


	@Override
	public PaymentDTO updateOrderForFailure(Map<String, String> responsePayload) {
		String razorPayOrderId = responsePayload.get("razor_pay_order_id");
		Payment order = paymentRepo.findByRazorPayOrderId(razorPayOrderId);
		order.setPaymentStatus("Failed");
		Payment updatedOrder = paymentRepo.save(order);
		PaymentDTO updatedDTO = modelMapper.map(updatedOrder, PaymentDTO.class);
		return updatedDTO;
	}
}
