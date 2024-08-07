package com.lancestack.entities;

import java.math.BigDecimal;

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
@Table(name = "ratings")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Rating extends BaseEntity {
	
	@Column
	private BigDecimal  rating;
	 
	@Column
	private String feedback;
	 
	@ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
	 
    @ManyToOne
    @JoinColumn(name = "rater_id")
    private User rater;
    
    @ManyToOne
    @JoinColumn(name = "ratee_id")
    private Freelancer ratee;

    @Column
    @Enumerated(EnumType.STRING)
    private RatingType ratingType;
    
}
