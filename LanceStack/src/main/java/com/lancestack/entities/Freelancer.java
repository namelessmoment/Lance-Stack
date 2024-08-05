package com.lancestack.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@Column
	@Email(message = "Please enter a valid email address")
	@NotBlank(message = "Enter Email")
	private String email;
	@Column
	@NotBlank(message = "Enter Password")
	private String password;
	@Column
	private String mobileNumber;
	@Column
	private String profileDescription;
	@Column
	private String skills;
	
	@OneToMany(mappedBy = "freelancer")
    private List<Bid> bids = new ArrayList<>();
	
	//helper method : to add bid
		public void addBid(Bid b) {
			this.bids.add(b);
			b.setFreelancer(this);
		}
}
