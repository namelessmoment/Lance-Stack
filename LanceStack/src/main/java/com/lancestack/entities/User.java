package com.lancestack.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
public class User extends BaseEntity {
	@Column(length = 40)
	private String userName;
//	@OneToMany(mappedBy = "chosenUser", cascade = CascadeType.ALL ,orphanRemoval = true)
//	private List<Project> projects = new ArrayList<>();
	@Column
//	@Email(message = "Please enter a valid email address")
//	@NotBlank(message = "Enter Email")
	private String email;
	@Column
//	@NotBlank(message = "Enter Password")
	private String password;
	@Column
	@Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number. It must be a 10-digit number.")
	private String mobileNumber;
}
