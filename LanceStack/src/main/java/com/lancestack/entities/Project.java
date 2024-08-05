package com.lancestack.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "projects")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties("contracts")
public class Project extends BaseEntity {
	@Column
	private String title;
	@Enumerated(EnumType.STRING)
	@Column
	private ProjectType projType;
	@Column
	private String description;
	@Enumerated(EnumType.STRING)
	@Column
	private ProjectStatus status;
	@Column
	private double budget;
	
	@OneToMany(mappedBy = "project",fetch = FetchType.EAGER)
    private List<Bid> bids = new ArrayList<>();
	
//	@OneToMany(mappedBy = "project")
//	private List<Contract> contracts = new ArrayList<>();
	
//	@OneToOne(mappedBy = "project")
//	@JoinColumn(name = "contract_id", nullable = true)
//	@JsonIgnore
//	private Contract contract;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	//helper method : to add bid
	public void addBid(Bid b) {
		this.bids.add(b);
		b.setProject(this);
	}
	
	//helper method : to get bids of the given project
	public List<Bid> getAllBids() {
        return this.bids;
    }
	
}
