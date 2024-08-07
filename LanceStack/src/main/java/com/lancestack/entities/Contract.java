package com.lancestack.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Contract extends BaseEntity {
	@Column(nullable = false)
    @CreationTimestamp
    private LocalDate startDate;

	@Column(nullable = false)
    private LocalDate endDate;

	@Column(nullable = false)
    private double paymentAmount;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ContractStatus status;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;
	
	// Check endpoints if directly Contract Entity used.
	@OneToMany(mappedBy = "contract")
	private List<Rating> ratings = new ArrayList<>();

	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id", nullable = false)
//	private User user;
}
