package com.lancestack.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private Contract contract;
	
	@Column
	private LocalDate paymentDate;
	
	@Column
	private double paymentAmount;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
}
