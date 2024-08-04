package com.lancestack.entities;

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
@Table(name = "bids")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Bid extends BaseEntity {
	
    private double bidAmount;
    private int daysWillTake;
    private String bidDescription;
    
    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;
}
