package com.lancestack.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("projects")
public class User extends BaseEntity {
	@Column(length = 40)
	private String userName;
	// if any issue occur after then look for LAZY TO EAGER Once.
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL ,orphanRemoval = true,fetch = FetchType.LAZY)
	private List<Project> projects = new ArrayList<>();
	@Column
	@Email(message = "Please enter a valid email address")
	@NotBlank(message = "Enter Email")
	private String email;
	@Column
	@NotBlank(message = "Enter Password")
	private String password;
	@Column
	@Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number. It must be a 10-digit number.")
	private String mobileNumber;
	
	// Check endpoints.
	@OneToMany(mappedBy = "rater")
	private List<Rating> ratingsGiven;

	
	//helper method to get all projects by user
	public List<Project> getAllProjects() {
        return this.projects;
    }
	
	// Helper method to add the rater for user.
	public void addRating(Rating rating) {
		this.ratingsGiven.add(rating);
		rating.setRater(this);
	}
	
	// Helper method to get all the rate for user.
	public List<Rating> getAllRatings(){
		return this.ratingsGiven;
	}
}
