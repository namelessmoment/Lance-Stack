package com.lancestack.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "otp_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTPEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String email;
    private String otp;
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    public OTPEntity(String email, String otp) {
		super();
		this.email = email;
		this.otp = otp;
	}
}
