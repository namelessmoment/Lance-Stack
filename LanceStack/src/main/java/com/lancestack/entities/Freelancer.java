package com.lancestack.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "freelancers")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("bids") // if this making problem in future write fetchtype = EAGER on List<bids>
public class Freelancer extends BaseEntity {
	@Column(length = 40)
	private String freelancerName;
	@Column(unique = true)
	@Email(message = "Please enter a valid email address")
	@NotBlank(message = "Enter Email")
	private String email;
	@Column
	@NotBlank(message = "Enter Password")
	private String password;
	@Column(unique = true)
	private String mobileNumber;
	@Column
	private String profileDescription;
	@Column
	private String skills;
	
	@OneToMany(mappedBy = "freelancer")
    private List<Bid> bids = new ArrayList<>();
	
	@OneToMany(mappedBy = "freelancer")
	private List<Contract> contracts = new ArrayList<>();
	
	// Check endpoints if there is directly Freelancer used.
	@OneToMany(mappedBy = "freelancer")
	private List<Rating> ratingsReceived;

	
	
	//helper method : to add bid
	public void addBid(Bid bid) {
		this.bids.add(bid);
		bid.setFreelancer(this);
	}
	
	// Helper Add method for Contract list.
	public void addContract(Contract contract) {
		this.contracts.add(contract);
		contract.setFreelancer(this);
	}
		
	//helper method : to get all contracts of the given project
	public List<Contract> getAllBids() {
        return this.contracts;
    }
		
		
}
