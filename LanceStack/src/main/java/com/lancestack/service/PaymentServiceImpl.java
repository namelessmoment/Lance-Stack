package com.lancestack.service;

import java.util.Map;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

	    Contract contract = contractRepo.findById(order.getContractId()).orElseThrow(null);
	    payment.setContractId(contract);
	    paymentRepo.save(payment);

	    // Map the saved payment entity to PaymentDTO
	    PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
	    return paymentDTO;
	}


	@Override
	public PaymentDTO updateOrder(Map<String, String> responsePayload) {
		String razorPayOrderId = responsePayload.get("razor_pay_order_id");
		Payment order = paymentRepo.findByRazorPayOrderId(razorPayOrderId);
		order.setPaymentStatus("Paid");
		Payment updatedOrder = paymentRepo.save(order);
		PaymentDTO updatedDTO = modelMapper.map(updatedOrder, PaymentDTO.class);
		return updatedDTO;
	}
}
