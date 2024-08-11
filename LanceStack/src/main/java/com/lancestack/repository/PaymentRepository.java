package com.lancestack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
	Payment findByRazorPayOrderId(String razorPayOrderId);
}
