package com.lancestack.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "payments")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "contract_id", nullable = false)
	private Contract contractId;
	
	@Column
	@CreationTimestamp
	private LocalDateTime paymentDateTime;
	
	@Column
	private Integer paymentAmount;
	
	@Column
//	@Enumerated(EnumType.STRING)
	private String paymentMethod;
	
	@Column
//	@Enumerated(EnumType.STRING)
	private String paymentStatus;
	
	@Column
	private String razorPayOrderId;
	
//	@Column
//	private String razorpayPaymentId;
}
