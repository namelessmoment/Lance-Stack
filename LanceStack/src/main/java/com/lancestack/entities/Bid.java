package com.lancestack.entities;

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
@Table(name = "bids")
@Getter
@Setter
@ToString(exclude = "project")  // Exclude project to prevent circular reference
@AllArgsConstructor
@NoArgsConstructor
public class Bid extends BaseEntity {
	
	@Column
    private double bidAmount;
	@Column
	private int daysWillTake;
	@Column	
	private String bidDescription;
    
    @ManyToOne
    @JoinColumn(name = "project_id")
//    @JsonIgnore
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;
}
